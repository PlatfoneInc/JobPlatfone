var app = angular.module('jobPlatfone', ['ngCookies', 'ngSanitize', 'ngRoute']);

//app.factory('myService', function($http) {
//	var myService = {
//		async : function($settings) {
//			// $http returns a promise, which has a then function, which also
//			// returns a promise
//			var promise = $http($settings).then(function(response) {
//				// The then function here is an opportunity to modify the
//				// response
//				console.log(response);
//				// The return value gets picked up by the then in the
//				// controller.
//				return response;
//			});
//			// Return the promise to the controller
//			return promise;
//		}
//	};
//	return myService;
//});

// app.config([ '$routeProvider', function($routeProvider) {
//	return $routeProvider.when('/linkedindialog', {
//		templateUrl : '/index',
//		controller : 'linkedinRedirect'
//	});
//} ]);

app.controller('loginNavCtrl', function($scope, $cookieStore) {
	console.log('loginNavCtrl');
	var userId = $cookieStore.get('user_id'); 
	if (typeof userId == undefined) {
		console.log('userId: ' + userId);
		$scope.loginInfo = $cookieStore.get('user_first_name');
	} else {
		$scope.loginInfo = 'Login(With Dialog)';
	}
});

app.controller('linkedinRedirect', function($scope, $routeParams) {
	console.log($routeParams);
	// $http({
	// method : 'GET',
	// url : data
	// }).success(function(data, status, headers, config) {
	//
	// });
});

app.controller('loginDialogCtrl', function($scope, $http, $window) {

	$scope.loginLinkedIn = function() {
		console.log('type -> linkedin');
		$http({
			method : 'POST',
			url : '/authorizationdialog'
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