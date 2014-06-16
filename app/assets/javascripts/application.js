var app = angular.module('jobPlatfone',
		[ 'ngCookies', 'ngSanitize', 'ngRoute', 'ngResource' ]);

app.factory('mySession', function() {
	var mySession = {
		userId : '',
		userFirstName : '',
		userPictureUrl : ''
	};
	return mySession;
});

app.factory('mySharedService', function($rootScope) {
	var sharedService = {};

	sharedService.message = '';

	sharedService.prepForBroadcast = function(msg) {
		this.message = msg;
		this.broadcastItem();
	};

	sharedService.broadcastItem = function() {
		$rootScope.$broadcast('handleBroadcast');
	};

	return sharedService;
});

app.factory('Auth', ['$cookieStore', function ($cookieStore) {

    var _user = {};

    return {

        user : _user,

        set: function (_user) {
            // you can retrive a user setted from another page, like login sucessful page.
            existing_cookie_user = $cookieStore.get('current.user');
            _user =  _user || existing_cookie_user;
            $cookieStore.put('current.user', _user);
        },

        remove: function () {
            $cookieStore.remove('current.user', _user);
        }
    };
}])

app.constant("apiUrl", "http://192.81.131.248:9000/api");

app.config([ "$routeProvider", function($routeProvider) {
	return $routeProvider.when("/", {
		templateUrl : "/views/index",
		controller : "listCtrl"
	}).when("/linkedin/:profileId", {
		templateUrl : "/views/index",
		controller : "profileCtrl"
	}).otherwise({
		redirectTo : "/"
	});
} ]);

app.config([ "$locationProvider", function($locationProvider) {
	// enable the new HTML5 routing and histoty API
	return $locationProvider.html5Mode(true).hashPrefix("!");
} ]);

// //the global controller
//app.controller("AppCtrl", ["$scope", "$location", function($scope, $location) {
//	// the very sweet go function is inherited to all other controllers
//	$scope.go = function (path) {
//		$location.path(path);
//	};
//}]);

app.directive("jobContent", function() {
	return {
		restrict : 'AE',
		replace : true,
		transclude : true,
		scope : {
			title : '=expanderTitle'
		},
		template : '<p>' 
			+ '<div ng-show="showMe" ng-transclude></div>'
			+ '<div ng-click="toggle()">show more</div>' 
			+ '</p>',
		link : function(scope, element, attrs) {
			scope.showMe = false;
			scope.toggle = function toggle() {
				scope.showMe = !scope.showMe;
			}
		}
	}
});

app.controller("listCtrl", [ "$scope", "$resource", "apiUrl",
		function($scope, $resource, apiUrl) {
			console.log('listCtrl');
			var Jobs = $resource(apiUrl + "/jobs"); // a RESTful-capable
													// resource object
			console.log(apiUrl + "/jobs");
			$scope.jobs = Jobs.query(); // for the list of jobs in main
		} ]);

app.controller("profileCtrl", [
		"$rootScope",
		"$resource",
		"$routeParams",
		"$cookieStore",
		"$location",
		"Auth",
		"mySession",
		"apiUrl",
		function($rootScope, $resource, $routeParams, $cookieStore, $location,
				Auth, mySession, apiUrl) {
			console.log('profileCtrl');
			// a RESTful-capable resource object
			var ShowProfile = $resource(apiUrl + "/profile/:profileId");
			console.log("routeparam:" + $routeParams.profileId);
			if (typeof $routeParams.profileId != undefined) {
				var profilePromise = ShowProfile.get({
					profileId : $routeParams.profileId
				});
				profilePromise.$promise.then(function(profile) {
					console.log("profile: " + JSON.stringify(profile));
					console.log("profile id: " + profile.id);
					if (profile.$resolved) {
						console.log("saving data");

						$location.path("/");

						console.log("userId: " + profile.id);
						console.log("userFirstName: " + profile.firstName);
						console.log("userPictureUrl: " + profile.pictureUrl);

//						mySession.userId = profile.id;
//						mySession.userFirstName = profile.firstName;
//						mySession.userPictureUrl = profile.pictureUrl;

						$rootScope.$emit('loginNavUpdate', profile);
						// Auth.set(profile.firstName);
					}
				});
			}
		} ]);

app.controller("loginNavCtrl", [ "$rootScope", "$scope", "$cookieStore", 
		function($rootScope, $scope, $cookieStore) {
			$rootScope.$on('loginNavUpdate', function(event, profile) {
				console.log('loginNavUpdate');
				$cookieStore.put("profile", profile);
				// console.log("Auth: ", JSON.stringify(Auth.user));
				console.log("userId: " + profile.id);
				console.log("userFirstName: " + profile.firstName);
				console.log("userPictureUrl: " + profile.pictureUrl);
				console.log($cookieStore.get("profile"));
			});
			console.log('loginNavCtrl');
			var profile = $cookieStore.get("profile");
			if (typeof profile == undefined) {
				console.log('userId: ' + profile.id);
				$scope.loginInfo = profile.firstName;
			} else {
				$scope.loginInfo = 'Login(With Dialog)';
			}
		} ]);

app.controller('loginDialogCtrl', function($scope, $http, $window) {

	$scope.loginLinkedIn = function() {
		console.log('type -> linkedin');
		$http({
			method : 'POST',
			url : '/authorization'
		}).success(function(data, status, headers, config) {
			console.log('data: ' + data);
			console.log('status: ' + status);
			console.log('headers: ' + headers);
			console.log('config: ' + config);
			// $location.path(data);
			// $location.url(data);
			$window.location.href = data;
		}).error(function(data, status, headers, config) {
			console.log('data: ' + data);
			console.log('status: ' + status);
			console.log('headers: ' + headers);
			console.log('config: ' + config);
		});
	};

	$scope.login = function() {

		console.log('type -> login');
		var bValid = true;

		bValid = bValid && checkLength($scope.name, "username", 3, 16);
		bValid = bValid && checkLength($scope.email, "email", 6, 80);
		bValid = bValid && checkLength($scope.password, "password", 5, 16);

		// bValid = bValid && checkRegexp(name, /^[a-z]([0-9a-z_])+$/i,
		// "Username may consist of a-z, 0-9, underscores, begin with a
		// letter.");
		// From jquery.validate.js (by joern),
		// contributed by Scott Gonzalez:
		// http://projects.scottsplayground.com/email_address_validation/
		// bValid = bValid && checkRegexp(email,
		// /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
		// "eg. ui@jquery.com");
		// bValid = bValid && checkRegexp(password, /^([0-9a-zA-Z])+$/,
		// "Password field only allow : a-z 0-9");
		// allFields.removeClass("ui-state-error");

		if (bValid) {
			// TODO success
			$("#dialog-form").dialog("close");
		}
	};

});