/**
 * This js is for login dialog control with AngularJS
 */
$(function() {

	$("#dialog-login").click(function() {
		$("#dialog-form").dialog("open");
	});

	$("#dialog-form")
			.dialog(
					{
						autoOpen : false,
						height : 300,
						width : 350,
						modal : true,
						close : function() {
							// allFields.val("").removeClass("ui-state-error");
						}
					});

});

function updateTips(t) {
	$('#loginMessage').text(t).addClass("ui-state-highlight");
	setTimeout(function() {
		$('#loginMessage').removeClass("ui-state-highlight", 1500);
	}, 500);
}

function checkLength(o, n, min, max) {
	if (o.length > max || o.length < min) {
		$('#loginMessage').addClass("ui-state-error");
		updateTips("Length of " + n + " must be between " + min + " and "
				+ max + ".");
		return false;
	} else {
		return true;
	}
}

function checkRegexp(o, regexp, n) {
	if (!(regexp.test(o))) {
		$('#loginMessage').addClass("ui-state-error");
		updateTips(n);
		return false;
	} else {
		return true;
	}
}

function setLoginType(type) {
	$("#request").val(type);
}