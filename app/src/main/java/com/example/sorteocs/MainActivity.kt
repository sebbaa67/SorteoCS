package com.example.sorteocs

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.example.sorteocs.Names.Name
import com.example.sorteocs.Names.NamesArchive
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //crea la vista y agrega el layout
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //inicializa el archivo de todos los nombres a sortear
        var lista: ArrayList<Name> = NamesArchive.getNamesArchive()

        //referencia todos los objetos de vista
        val checkBoxHabPicker: CheckBox = findViewById(R.id.check_cat)
        val numberPicker: NumberPicker = findViewById(R.id.expandible_cat)
        val textViewNombres: TextView = findViewById(R.id.text_ingrese_nombre)
        val editTextNombres: EditText = findViewById(R.id.edit_text_a_sortear)
        val buttonEdit: Button = findViewById(R.id.boton_ver_participantes)
        val buttonComenzar: Button = findViewById(R.id.boton_comenzar_sorteo)

        //deshabilitar selector de numeros
        numberPicker.isEnabled = false

        //rueda de selector de numeros activa
        numberPicker.wrapSelectorWheel = true

        //valores minimos y maximos de selector de numeros
        numberPicker.minValue = 1
        numberPicker.maxValue = 10

        editTextNombres.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when(actionId){
                //si cuando se ingresa un nombre se da SEND recopila la info la gurda y borra el edittext
                EditorInfo.IME_ACTION_SEND -> {
                if (lista.isEmpty()) {

                    if ( numberPicker.isEnabled){
                        lista.add(Name(editTextNombres.text.toString(), numberPicker.value))
                        checkBoxHabPicker.isClickable = false
                    } else{
                        lista.add(Name(editTextNombres.text.toString()))
                        checkBoxHabPicker.isEnabled = false
                    }

                } else{
                    lista.add(Name(editTextNombres.text.toString(), if (numberPicker.isEnabled) numberPicker.value else 1 ))
                }
                    println("Se agrega un nuevo valor de nombre a la lista")
                    editTextNombres.text.clear()
                    editTextNombres.clearAnimation()
                    println("se borra el texto del edit text")
                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
                            as InputMethodManager
                    imm.hideSoftInputFromWindow(editTextNombres.windowToken,0)
                    println("se oculta el teclado")
                    true
                }
                else -> false
            }
        }
        checkBoxHabPicker.setOnClickListener{
            //si el checkbox esta habilitado, se habilita el selector de numeros
                numberPicker.isEnabled = checkBoxHabPicker.isChecked
                println("Selector de numeros: ${numberPicker.isEnabled}")

        }
        numberPicker.setOnValueChangedListener{picker, oldVal, newVal ->
            //se actualiza el valor del selector de numeros
            picker.value = newVal
            println("el nuevo valor del picker es: ${picker.value}")
        }
        buttonEdit.setOnClickListener{
            lista.clear()
            checkBoxHabPicker.isEnabled = true
            checkBoxHabPicker.isClickable = true
            checkBoxHabPicker.isChecked = false

        }
        buttonComenzar.setOnClickListener{
            if ( lista.size > 1 && lista.isNotEmpty()) {
                if (numberPicker.isEnabled) {
                    for (i in 1..10) {
                        var listaProvisoria: ArrayList<Name> = ArrayList(0)
                        for (j in 0 until lista.size) {
                            if ( lista[j].categoria.equals(i)){
                                listaProvisoria.add(lista[j])

                            }

                        }
                        for (k in 0 until listaProvisoria.size){
                            val a = listaProvisoria.random()
                            println("Categoria: ${i}  Orden: ${k + 1}  Nombre: ${a.name}")
                            listaProvisoria.remove(a)
                        }
                    }
                } else {
                    var listaProvisoria: ArrayList<Name> = lista
                        for (i in 0 until listaProvisoria.size) {
                            var a: Name = listaProvisoria.random()
                            println("${i + 1}: ${a.name}")
                            listaProvisoria.remove(a)
                    }
                }
            }
        }
    }


}
