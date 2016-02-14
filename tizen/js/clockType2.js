var canvas, context, clockRadius, isAmbientMode;

window.requestAnimationFrame = window.requestAnimationFrame
		|| window.webkitRequestAnimationFrame
		|| window.mozRequestAnimationFrame || window.oRequestAnimationFrame
		|| window.msRequestAnimationFrame || function(callback) {
			'use strict';
			window.setTimeout(callback, 1000 / 60);
		};
		
var default_font_size = 30;

function renderDots() {
	'use strict';

	var dx = 0, dy = 0, i = 1, angle = null;

	context.save();

	// Assigns the clock creation location in the middle of the canvas
	context.translate(canvas.width / 2, canvas.height / 2);

	// Assign the style of the number which will be applied to the clock plate
	
	
	for (i = 1; i <= 60; i++) {
		angle = (i - 3) * (Math.PI * 2) / 60;
		var start_dx = clockRadius * 0.99 * Math.cos(angle);
		var start_dy = clockRadius * 0.99 * Math.sin(angle);
		
		var line_width, end_dx, end_dy;
		if (i % 5 == 3) {
			end_dx = clockRadius * 0.80 * Math.cos(angle);
			end_dy = clockRadius * 0.80 * Math.sin(angle);
			line_width = 10;
		} else {
			end_dx = clockRadius * 0.89 * Math.cos(angle);
			end_dy = clockRadius * 0.91 * Math.sin(angle);
			line_width = 6;
		}

		context.beginPath();
		context.lineWidth = line_width;
		context.strokeStyle = '#000';
		context.moveTo(start_dx, start_dy);
		context.lineTo(end_dx, end_dy);
		context.closePath();
		context.stroke();
		
	}
}

function renderHourNeedle(hour) {
	'use strict';

	var angle = null, radius = null, start_needle = null;

	angle = (hour - 3) * (Math.PI * 2) / 12;
	radius = clockRadius * 0.70;
	start_needle = clockRadius * 0.20 * -1;
	
	context.save();
	context.rotate(angle);
	context.beginPath();
	var lineEndWidth = 26;
	var lineStartWidth = 22;

	context.strokeStyle = '#000';
	context.moveTo(start_needle, lineEndWidth / 2 * -1);
	context.lineTo(start_needle, lineEndWidth / 2);
	context.lineTo(radius, lineStartWidth / 2);
	context.lineTo(radius, lineStartWidth / 2 * -1);
	
	//if (!isAmbientMode) {
		context.shadowColor = '#777';
		context.shadowBlur = 5;
		context.shadowOffsetX = 2;
		context.shadowOffsetY = 1;
	//}
	context.fill();
	context.closePath();
	context.restore();
}

function renderMinuteNeedle(minute) {
	'use strict';

	var angle = null, radius = null, start_needle = null;

	angle = (minute - 15) * (Math.PI * 2) / 60;
	radius = clockRadius * 0.90;
	start_needle = clockRadius * 0.20 * -1;

	context.save();
	context.rotate(angle);
	context.beginPath();
	var lineEndWidth = 22;
	var lineStartWidth = 18;

	context.strokeStyle = '#000';
	context.moveTo(start_needle, lineEndWidth / 2 * -1);
	context.lineTo(start_needle, lineEndWidth / 2);
	context.lineTo(radius, lineStartWidth / 2);
	context.lineTo(radius, lineStartWidth / 2 * -1);
	
	//if (!isAmbientMode) {
		context.shadowColor = '#777';
		context.shadowBlur = 5;
		context.shadowOffsetX = 2;
		context.shadowOffsetY = 1;
	//}

	context.fill();
	context.closePath();
	context.restore();
}

function renderSecondNeedle(second) {
	'use strict';

	var angle = null, radius = null, start_needle = null;

	angle = (second - 15) * (Math.PI * 2) / 60;
	radius = clockRadius * 0.70;
	start_needle = clockRadius * 0.25 * -1;
	
	context.save();

	// render Needle
	context.rotate(angle);
	context.beginPath();
	context.strokeStyle = '#ff0000';
	context.lineWidth = 3;
	context.moveTo(start_needle, 0);
	context.lineTo(radius, 0);
	context.closePath();
	//if (!isAmbientMode) {
		context.shadowColor = '#777';
		context.shadowBlur = 5;
		context.shadowOffsetX = 2;
		context.shadowOffsetY = 1;
	//}
	context.stroke();
	context.closePath();
	context.restore();
	
	// render Needle end circle
	context.beginPath();
	context.fillStyle = '#ff0000';
	var dx = radius * Math.cos(angle);
	var dy = radius * Math.sin(angle);
	context.arc(dx, dy, 12, 0, 2 * Math.PI, false);
	if (!isAmbientMode) {
		context.shadowColor = '#777';
		context.shadowBlur = 5;
		context.shadowOffsetX = 2;
		context.shadowOffsetY = 1;
	}
	context.fill();
	context.closePath();
	
	// Draw Center Circle
	context.beginPath();
	context.fillStyle = '#ff0000';
	context.arc(0, 0, 6, 0, 2 * Math.PI, false);
	context.fill();
	context.closePath();
	
	// Draw Center Small Circle
	context.beginPath();
	context.fillStyle = '#666';
	context.arc(0, 0, 2, 0, 2 * Math.PI, false);
	context.fill();
	context.closePath();
}

function renderDate(date) {
	// draw square
	context.beginPath();
	context.lineWidth = 1;
	context.strokeRect(60, -20, 80, 40);
	
	context.font = "30pt Arial Bold";
	
	var front = Math.floor(date / 10);
	var end = date % 10;
	context.fillText(front, 70, 12);
	context.fillText(end, 105, 12);
	context.closePath();
}

function getDate() {
	'use strict';

	var date;
	try {
		date = tizen.time.getCurrentDateTime();
	} catch (err) {
		console.error('Error: ', err.message);
		date = new Date();
	}

	return date;
}

function watch() {
	'use strict';

	if (isAmbientMode === true) {
		return;
	}

	// Import the current time
	// noinspection JSUnusedAssignment
	var date = getDate(), hours = date.getHours(), minutes = date.getMinutes(), seconds = date
			.getSeconds(), hour = hours + minutes / 60, minute = minutes
			+ seconds / 60, nextMove = 1000 - date.getMilliseconds();

	
	
	// Erase the previous time
	context.clearRect(0, 0, context.canvas.width, context.canvas.height);

	renderDots();
	renderDate(date.getDate());
	renderHourNeedle(hour);
	renderMinuteNeedle(minute);
	renderSecondNeedle(seconds);

	context.restore();

	setTimeout(function() {
		window.requestAnimationFrame(watch);
	}, nextMove);
}

function ambientWatch() {
	'use strict';

	// Import the current time
	// noinspection JSUnusedAssignment
	var date = getDate(), hours = date.getHours(), minutes = date.getMinutes(), seconds = date
			.getSeconds(), hour = hours + minutes / 60, minute = minutes
			+ seconds / 60;

	// Erase the previous time
	context.clearRect(0, 0, context.canvas.width, context.canvas.height);

	renderDots();
	renderDate(date.getDate());
	renderHourNeedle(hour);
	renderMinuteNeedle(minute);

	context.restore();
}

window.onload = function onLoad() {
	'use strict';

	canvas = document.querySelector('.clock');
	context = canvas.getContext('2d');
	clockRadius = document.body.clientWidth / 2;

	// Assigns the area that will use Canvas
	canvas.width = document.body.clientWidth;
	canvas.height = canvas.width;

	// add eventListener for tizenhwkey
	window.addEventListener('tizenhwkey', function(e) {
		if (e.keyName === 'back') {
			try {
				tizen.application.getCurrentApplication().exit();
			} catch (err) {
				console.error('Error: ', err.message);
			}
		}
	});

	// add eventListener for timetick
	window.addEventListener('timetick', function() {
		console.log("timetick is called");
		ambientWatch();
	});

	// add eventListener for ambientmodechanged
	window.addEventListener('ambientmodechanged', function(e) {
		console.log("ambientmodechanged : " + e.detail.ambientMode);
		if (e.detail.ambientMode === true) {
			// rendering ambient mode case
			isAmbientMode = true;
			ambientWatch();

		} else {
			// rendering normal case
			isAmbientMode = false;
			window.requestAnimationFrame(watch);
		}
	});

	// normal case
	isAmbientMode = false;
	window.requestAnimationFrame(watch);
};
