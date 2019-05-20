package io.khasang.ba.service;

import io.khasang.ba.entity.Address;

import java.util.List;

public interface AddressService {
    /**
     * method for add address
     *
     * @param address = address for adding
     * @return created address
     */
    Address addAddress(Address address);

    /**
     * method for getting address by specific id
     *
     * @param id - address
     * @return address by id
     */
    Address getAddressById(long id);

    /**
     * method gor getting all addresses
     *
     * @return all addresses
     */
    List<Address> getAllAddresses();

    /**
     * method for update address
     *
     * @param address - address update
     * @return updated address
     */
    Address updateAddress(Address address);

    /**
     * method for delete address by id
     *
     * @param id - address id for delete
     * @return deleted address
     */
    Address deleteAddress(long id);
}
