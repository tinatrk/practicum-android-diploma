package ru.practicum.android.diploma.data.converter

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.embedd.ContactsEmbeddable
import ru.practicum.android.diploma.data.dto.models.ContactsDto
import ru.practicum.android.diploma.domain.models.vacancy.Contacts
import ru.practicum.android.diploma.domain.models.vacancy.Phone
import java.lang.reflect.Type

class ContactsConverter(private val gson: Gson) {
    fun map(contactsDto: ContactsDto?): Contacts? {
        return contactsDto?.let {
            Contacts(
                id = it.id,
                name = it.name,
                email = it.email,
                phone = it.phones?.map { phone ->
                    Phone(
                        phone.comment,
                        phone.formatted
                    )
                }
            )
        }
    }

    fun map(contacts: ContactsEmbeddable?): Contacts? {
        return contacts?.let {
            Contacts(
                id = contacts.id,
                name = contacts.name,
                email = contacts.email,
                phone = getListFromString(contacts.phone)
            )
        }
    }

    fun map(contacts: Contacts?): ContactsEmbeddable? {
        return contacts?.let {
            ContactsEmbeddable(
                id = contacts.id,
                name = contacts.name ?: EMPTY_STRING,
                email = contacts.email ?: EMPTY_STRING,
                phone = convertListToString(contacts.phone) ?: EMPTY_STRING
            )
        }
    }

    private fun convertListToString(list: List<Phone>?): String? {
        return list?.let { gson.toJson(list) }
    }

    private fun getListFromString(str: String): List<Phone>? {
        val type: Type = object : TypeToken<List<Phone>>() {}.type
        return try {
            gson.fromJson(str, type)
        } catch (e: JsonSyntaxException) {
            Log.e(VACANCY_CONVERTER_TAG, "Не удалось получить список из строки (json)")
            null
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val VACANCY_CONVERTER_TAG = "VacancyConverter"
    }
}
