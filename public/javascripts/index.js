// Making date picker and format date
$(function() {
	$("#datepicker").datepicker();
	$("#datepicker").datepicker("option", "dateFormat", "yy-mm-dd");
});

/*
// Parsing Jobs from elasticsearch
// Using JSON format
$.each($.parseJSON(jobs_json), function(i, job) {
	// job = job.jobs;
	var id_part = "job-id-" + job.id;
	$("#job-info").append('<hr>' + '<div id="' + id_part + '"></div>');

	// Making What
	// Get inner List
	//	var res = "<ul>";
	//	$.each(job.what.responsibilities, function(res_i, responsibility) {
	//		res = res + "<li>" + responsibility + "</li>";
	//	});
	//	res += "</ul>";
	//
	//	var des_cha = "<ul>";
	//	$.each(job.what.desired_characteristics, function(des_cha_i, desired_characteristic) {
	//		des_cha = des_cha + "<li>" + desired_characteristic + "</li>";
	//	});
	//	des_cha += "</ul>";
	//
	//	var qua = "<ul>";
	//	$.each(job.what.qualifications, function(qua_i, qualification) {
	//		qua = qua + "<li>" + qualification + "</li>";
	//	});
	//	qua += "</ul>";
	//
	//	var add_qua = "<ul>";
	//	$.each(job.what.additional_qualifications, function(add_qua_i, additional_qualification) {
	//		add_qua = add_qua + "<li>" + additional_qualification + "</li>";
	//	});
	//	add_qua += "</ul>";

	// Make What
	var job_what = 
	"<div class='row'>" +
		"<div class='col-md-1'></div>" +
		"<div class='col-md-1'><p class='label label-warning'> What </p></div>" +
		"<div class='col-md-2'>" +
			"<p class='label label-default'> Title </p>" +
			"<p>" + job.what.title + "</p>" +
		"</div>" +
		"<div class='col-md-3'>" +
			"<p class='label label-default'> Description </p>" +
			"<p>" + job.what.description + "</p>" +
		"</div>" +
		"<div class='col-md-4'>" +
			"<p class='label label-default'> Content </p>" +
			"<p>" + job.what.content + "</p>" +
		"</div>" +
	"</div>";

	// Making content for Who Where When combine with "What" 
	var job_content = 
		'<div class="row"><p><div class="label label-info">' + job.what.title + '</div></p></div>' + 
		"<div class='row'>"+
			"<div class='col-md-1'></div>" +
			"<div class='col-md-1'><p class='label label-warning'> Who </p></div>" +
			"<div class='col-md-2'><p>" + job.who.company + "</p></div>" +
			"<div class='col-md-4'>" +
				"<div class='row'>" +
					"<p>" + '<a href="' + job.who.url + '" class="label label-primary">detail</a></p>'+
				"</div>" +
			"</div>" +
			"<div class='col-md-4'></div>" +
		"</div>" +
		"<div class='row'>"+
			"<div class='col-md-2'></div>" +
			"<div class='col-md-10'><p>" + job.who.description + "</p></div>" +
		"</div>" +
		job_what +
		"<div class='row'>"+
			"<div class='col-md-1'></div>" +
			"<div class='col-md-1'><p class='label label-warning'> Where </p></div>" +
			"<div class='col-md-2'><p>" + job.where.city + "</p></div>" +
			"<div class='col-md-4'><p>" + job.where.state + "</p></div>" +
			"<div class='col-md-4'><p>" + job.where.country + "</p></div>" +
		"</div>" +
		"<div class='row'>"+
			"<div class='col-md-2'></div>" +
			"<div class='col-md-4'>"+
				"<div class='row'>" +
					"<p class='label label-warning'>" + job.timestamp + "</p>"+
				"</div>" +
			"</div>" +
			"<div class='col-md-3'></div>" +
			"<div class='col-md-3'></div>" +
		"</div>";

	// Add all data into it's own div by id
	var job_div = document.getElementById(id_part);
	job_div.innerHTML += job_content;
});
*/