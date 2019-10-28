'use strict'

var colorHash = new ColorHash()
colorHash.hex('str')
document.querySelectorAll('.name').forEach(function(e) {
	console.log(colorHash.hex(e.textContent))
	e.parentElement.style.background = colorHash.hex(e.textContent)
})
//# sourceMappingURL=main.js.map
