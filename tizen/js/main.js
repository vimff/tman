var canvas, context, clockRadius, isAmbientMode;

window.requestAnimationFrame = window.requestAnimationFrame
		|| window.webkitRequestAnimationFrame
		|| window.mozRequestAnimationFrame || window.oRequestAnimationFrame
		|| window.msRequestAnimationFrame || function(callback) {
			'use strict';
			window.setTimeout(callback, 1000 / 60);
		};
		
var default_font_size = 30;

function renderCenterDot() {
	'use strict';
	// Render center dot
	context.beginPath();
	context.fillStyle = '#fff';
	context.lineWidth = 4;
	context.arc(0, 0, 10, 0, 2 * Math.PI, false);
	context.fill();
	
	context.closePath();
}

function renderDots() {
	'use strict';

	var dx = 0, dy = 0, i = 1, angle = null;

	context.save();

	// Assigns the clock creation location in the middle of the canvas
	context.translate(canvas.width / 2, canvas.height / 2);

	// Assign the style of the number which will be applied to the clock plate
	context.beginPath();

	// Create outer dots in a circle
	for (i = 1; i <= 12; i++) {
		angle = (i - 3) * (Math.PI * 2) / 12;
		dx = clockRadius * 0.9 * Math.cos(angle);
		dy = clockRadius * 0.9 * Math.sin(angle);
		if (i % 3 == 0) {
			context.font = "30pt Arial Bold";
			context.fillStyle = "#fff";
			dx = dx - (default_font_size / 2) + 3; // point radius
			dy = dy + (default_font_size / 2);

			if (i == 12) {
				dx -= 7.5;
			}

			context.fillText(i, dx, dy);
		}
		else {
			context.fillStyle = '#999999';
			context.arc(dx, dy, 3, 0, 2 * Math.PI, false);
			context.fill();
		}
	}
	context.closePath();

	renderCenterDot();
}

function renderAmbientDots() {
	'use strict';

	context.save();

	// Assigns the clock creation location in the middle of the canvas
	context.translate(canvas.width / 2, canvas.height / 2);

	renderCenterDot();
}

function renderNeedle(angle, radius, type) {
	'use strict';
	context.save();
	context.rotate(angle);
	context.beginPath();
	context.lineCap="round";

	switch(type) {
	case 'hour': context.lineWidth = 12; break;
	case 'minute': context.lineWidth = 5; break;
	case 'second': context.lineWidth = 3; break;	
	}
	
	
	context.strokeStyle = '#fff';
	context.moveTo(6, 0);
	context.lineTo(radius, 0);
	context.closePath();
	context.stroke();
	context.closePath();
	context.restore();
}

function renderHourNeedle(hour) {
	'use strict';

	var angle = null, radius = null;

	angle = (hour - 3) * (Math.PI * 2) / 12;
	radius = clockRadius * 0.55;
	renderNeedle(angle, radius, 'hour');
}

function renderMinuteNeedle(minute) {
	'use strict';

	var angle = null, radius = null;

	angle = (minute - 15) * (Math.PI * 2) / 60;
	radius = clockRadius * 0.70;
	renderNeedle(angle, radius, 'minute');
}

function renderSecondNeedle(second) {
	'use strict';

	var angle = null, radius = null;

	angle = (second - 15) * (Math.PI * 2) / 60;
	radius = clockRadius * 0.80;
	renderNeedle(angle, radius, 'second');
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

	renderAmbientDots();
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
