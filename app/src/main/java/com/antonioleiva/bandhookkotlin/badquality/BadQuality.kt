package com.antonioleiva.bandhookkotlin.badquality

/**
 * Created by philipphofmann on 03/05/2017.
 */
class BadQuality {
	private var constant = "test"
	private var a = "test"
	private var b = "test"
	private var c = "test"
	private var d = "test"
	private var f = "test"

	fun tooManyArguments(lba: String, nope:String ) {
		if(lba == "") {
			if(nope == "what else") {

				while(true) {
					if(lba.isNotBlank()) {
						return Unit
					}
				}
			}
		}
	}
}