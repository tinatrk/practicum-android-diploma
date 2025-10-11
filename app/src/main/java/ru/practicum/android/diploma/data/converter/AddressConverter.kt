package ru.practicum.android.diploma.data.converter

import ru.practicum.android.diploma.data.db.embedd.AddressEmbeddable
import ru.practicum.android.diploma.data.dto.models.AddressDto
import ru.practicum.android.diploma.domain.models.Address

class AddressConverter {
    fun map(addressDto: AddressDto?): Address? {
        return addressDto?.let {
            Address(
                city = it.city,
                street = it.street,
                building = it.building,
                fullAddress = it.raw
            )
        }
    }

    fun map(address: Address?): AddressEmbeddable? {
        return address?.let {
            AddressEmbeddable(
                city = address.city ?: EMPTY_STRING,
                street = address.street ?: EMPTY_STRING,
                building = address.building ?: EMPTY_STRING,
                fullAddress = address.fullAddress ?: EMPTY_STRING
            )
        }
    }

    fun map(address: AddressEmbeddable?): Address? {
        return address?.let {
            Address(
                city = address.city,
                street = address.street,
                building = address.building,
                fullAddress = address.fullAddress
            )
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}
