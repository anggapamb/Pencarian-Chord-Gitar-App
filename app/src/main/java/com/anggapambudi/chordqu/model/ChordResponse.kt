package com.anggapambudi.chordqu.model

import com.google.gson.annotations.SerializedName

data class ChordResponse(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("creator")
	val creator: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
