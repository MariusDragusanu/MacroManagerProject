package com.example.macromanager.Adapters


data class __Serving( var Name:String="Piece",
                      val UnitOfMeasure:String="Gram",
                      var quantity:Double=100.0) {
    init{
        if(quantity>1) {
            Name += "(${quantity} ${UnitOfMeasure}s) "
        }
        else{
            Name += "(${quantity.toInt()} ${UnitOfMeasure})"
        }
    }
}
