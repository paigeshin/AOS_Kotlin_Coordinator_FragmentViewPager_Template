package com.paigesoftware.myapplication

data class Dessert(val name: String, var description: String) {

    var firstLetter: String = name[0].toString()

    companion object {
        fun prepareDesserts(names: Array<String>, descriptions: Array<String>): List<Dessert> {
            val desserts: MutableList<Dessert> = ArrayList(names.size)
            for (i in names.indices) {
                val dessert = Dessert(names[i], descriptions[i])
                desserts.add(dessert)
            }
            return desserts
        }
    }

}