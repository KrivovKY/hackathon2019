'use strict'

function _toConsumableArray(arr) {
	return _arrayWithoutHoles(arr) || _iterableToArray(arr) || _nonIterableSpread()
}

function _nonIterableSpread() {
	throw new TypeError('Invalid attempt to spread non-iterable instance')
}

function _iterableToArray(iter) {
	if (Symbol.iterator in Object(iter) || Object.prototype.toString.call(iter) === '[object Arguments]')
		return Array.from(iter)
}

function _arrayWithoutHoles(arr) {
	if (Array.isArray(arr)) {
		for (var i = 0, arr2 = new Array(arr.length); i < arr.length; i++) {
			arr2[i] = arr[i]
		}
		return arr2
	}
}

// ymaps.ready(init);
function init() {
	var myMap = new ymaps.Map(
		'map',
		{
			center: [55.745508, 37.435225],
			zoom: 13
		},
		{
			searchControlProvider: 'yandex#search'
		}
	) // let line = []
	// points.forEach((p) => {

	var info = {}
	var count = 1000

	var _loop = function _loop(i) {
		var _loop2 = function _loop2(j) {
			if (j > i)
				setTimeout(function() {
					var x = i
					var y = j
					ymaps.route([[points[x][0], points[x][1]], [points[y][0], points[y][1]]]).then(
						function(route) {
							// myMap.geoObjects.add(route);
							for (var i = 0; i < route.getPaths().getLength(); i++) {
								var way = route.getPaths().get(i)
								info = {
									X: x,
									Y: y,
									TimeProb: way.getHumanJamsTime(),
									TimeProbSec: way.getJamsTime() | 0,
									Time: way.getHumanTime(),
									TimeSec: (way.getTime() / 60) | 0,
									WayLen: way.getHumanLength(),
									WayLenMetr: way.getLength() | 0
								}
								var path = []
								var segments = way.getSegments() // console.log(segments)

								for (var j = 0; j < segments.length; j++) {
									// console.log('segmenr: ', j, segments[j].getCoordinates());
									var seg = segments[j].getCoordinates()
									seg.shift() // path.push(seg[seg.length/2|0])

									path.push.apply(path, _toConsumableArray(seg))
								}

								info['Way'] = JSON.stringify(path)
							}

							console.count('fetch')
							fetch('/path', {
								method: 'POST',
								headers: {
									Accept: 'application/json',
									'Content-Type': 'application/json'
								},
								body: JSON.stringify(info)
							})
						},
						function(error) {
							console.error('Er ошибка: ' + error.message)
						}
					)
				}, count)
		}

		for (var j = 0; j < 41; j++) {
			_loop2(j)
		}

		count += 5000
	}

	for (var i = 0; i < 41; i++) {
		_loop(i)
	} // 	line.push(out)
	// });
	// main.push(line)
} // var points = route.getWayPoints(),
// 	lastPoint = points.getLength() - 1;
//# sourceMappingURL=index.js.map
