package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.AddressDTO;
import com.vf.tickettothemoon_BackEnd.domain.dto.ClientDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Address;
import com.vf.tickettothemoon_BackEnd.domain.model.Client;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    // Mappage des propriétés entre Client et ClientDTO
    ClientDTO toClientDTO(Client client);

    Client toClient(ClientDTO clientDTO);

    @IterableMapping(elementTargetType = ClientDTO.class)
    List<ClientDTO> toClientDTOs(Iterable<Client> clients);

    @IterableMapping(elementTargetType = Client.class)
    List<Client> toClients(Iterable<ClientDTO> clientDTOs);

    //////////////////////////////
    // Méthodes de mappage pour address
    //////////////////////////////
    // Utilisation de AddressMapper pour mapper Address
    AddressMapper ADDRESS_MAPPER = Mappers.getMapper(AddressMapper.class);

    default AddressDTO toAddressDTO(Address address) {
        if (address == null) {
            return null;
        }
        return ADDRESS_MAPPER.toAddressDTO(address);
    }

    default Address toAddress(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return null;
        }
        return ADDRESS_MAPPER.toAddress(addressDTO);
    }

    // Mappage for Address with Client and its DTO
    default AddressDTO toAddressDTO(Client client) {
        if (client == null) {
            return null;
        }
        return toAddressDTO(client.getAddress());
    }

    default Address toAddress(ClientDTO clientDTO) {
        if (clientDTO == null) {
            return null;
        }
        return toAddress(clientDTO.getAddress());
    }

}
