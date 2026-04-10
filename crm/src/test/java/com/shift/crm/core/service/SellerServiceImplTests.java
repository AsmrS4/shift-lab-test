package com.shift.crm.core.service;

import com.shift.crm.api.models.requests.CreateSellerRequest;
import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.api.models.requests.UpdateSellerRequest;
import com.shift.crm.core.exceptions.BadRequestException;
import com.shift.crm.core.exceptions.constants.ExceptionMessages;
import com.shift.crm.core.persistence.entities.Seller;
import com.shift.crm.core.persistence.repositories.SellerRepository;
import com.shift.crm.core.service.impl.SellerServiceImpl;
import com.shift.crm.core.utils.PageableConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SellerServiceImplTests {
    @Mock
    private SellerRepository repository;
    @Mock
    private MockedStatic<PageableConverter> pageableConverterMock;
    @InjectMocks
    private SellerServiceImpl service;

    private Seller seller;
    private CreateSellerRequest createRequest;
    private UpdateSellerRequest updateRequest;
    private PaginationParams params;


    private final Long MOCKED_ID = 100L;
    private final String MOCKED_NAME = "John Doe";
    private final String MOCKED_CONTACT_INFO = "johndoe@example.com";
    private final String MOCKED_REGISTER_DATE = "2026-04-10T01:03:15.123";
    private final String MOCKED_CREATE_NAME_PARAM = "New user";
    private final String MOCKED_CREATE_CONTACT_INFO = "new@example.com";
    private final String MOCKED_UPDATE_NAME = "John Doe updated";
    private final String MOCKED_UPDATE_CONTACT_INFO = "updated@example.com";
    private final int MOCKED_SIZE = 5;
    private final int MOCKED_PAGE = 0;

    @BeforeEach
    public void setUp() {
        initSeller();
        initCreateRequest();
        initUpdateRequest();
        initPaginationParam();
    }

    private void initSeller() {
        seller = new Seller();
        seller.setId(MOCKED_ID);
        seller.setName(MOCKED_NAME);
        seller.setContactInfo(MOCKED_CONTACT_INFO);
        seller.setActive(true);
        seller.setRegistrationDate(LocalDateTime.parse(MOCKED_REGISTER_DATE));
        seller.setModifiedAt(null);
    }

    private void initCreateRequest() {
        createRequest = new CreateSellerRequest();
        createRequest.setName(MOCKED_CREATE_NAME_PARAM);
        createRequest.setContactInfo(MOCKED_CREATE_CONTACT_INFO);
    }

    private void initUpdateRequest() {
        updateRequest = new UpdateSellerRequest();
        updateRequest.setName(MOCKED_UPDATE_NAME);
        updateRequest.setContactInfo(MOCKED_UPDATE_CONTACT_INFO);
    }

    private void initPaginationParam() {
        params = new PaginationParams(MOCKED_SIZE, MOCKED_PAGE);
    }

    @Test
    public void retrieveSellerDetails_WhenSellerExistsAndActive_ShouldReturnSeller() {
        when(repository.existsById(MOCKED_ID)).thenReturn(true);
        when(repository.isActive(MOCKED_ID)).thenReturn(true);
        when(repository.getReferenceById(MOCKED_ID)).thenReturn(seller);

        Seller foundSeller = service.retrieveSellerDetails(MOCKED_ID);

        assertThat(foundSeller).isNotNull();
        assertThat(foundSeller.getId()).isEqualTo(MOCKED_ID);
        assertThat(foundSeller.getName()).isEqualTo(MOCKED_NAME);
        assertThat(foundSeller.getContactInfo()).isEqualTo(MOCKED_CONTACT_INFO);

        verify(repository).existsById(MOCKED_ID);
        verify(repository).isActive(MOCKED_ID);
        verify(repository).getReferenceById(MOCKED_ID);
    }

    @Test
    public void retrieveSellerDetails_WhenSellerExistsAndNonActive_ShouldThrowBadRequestException() {
        when(repository.existsById(MOCKED_ID)).thenReturn(true);
        when(repository.isActive(MOCKED_ID)).thenReturn(false);

        assertThatThrownBy(() -> service.retrieveSellerDetails(MOCKED_ID))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionMessages.ENTITY_DELETED_MSG);

        verify(repository).existsById(MOCKED_ID);
        verify(repository).isActive(MOCKED_ID);
        verify(repository, never()).getReferenceById(MOCKED_ID);
    }

    @Test
    public void retrieveSellerDetails_WhenSellerDoesNotExist_ShouldThrowNoSuchElementException() {
        when(repository.existsById(MOCKED_ID)).thenReturn(false);

        assertThatThrownBy(() -> service.retrieveSellerDetails(MOCKED_ID))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(ExceptionMessages.SELLER_NOT_FOUND_MSG);

        verify(repository).existsById(MOCKED_ID);
        verify(repository, never()).isActive(MOCKED_ID);
        verify(repository, never()).getReferenceById(MOCKED_ID);
    }

    @Test
    public void createSeller_ShouldSaveNewSeller() {
        Seller saved = new Seller();
        saved.setId(10L);
        saved.setName(MOCKED_CREATE_NAME_PARAM);
        saved.setContactInfo(MOCKED_CREATE_CONTACT_INFO);
        saved.setActive(true);

        when(repository.save(any(Seller.class))).thenReturn(saved);

        service.createSeller(createRequest);

        verify(repository).save(any(Seller.class));
    }

    @Test
    public void createSeller_ShouldReturnCreatedSeller() {
        Seller saved = new Seller();
        saved.setId(10L);
        saved.setName(MOCKED_CREATE_NAME_PARAM);
        saved.setContactInfo(MOCKED_CREATE_CONTACT_INFO);
        saved.setActive(true);

        when(repository.save(any(Seller.class))).thenReturn(saved);

        Seller created = service.createSeller(createRequest);

        assertThat(created).isNotNull();
        assertThat(created.getName()).isEqualTo(createRequest.getName());
        assertThat(created.getContactInfo()).isEqualTo(createRequest.getContactInfo());

        verify(repository).save(any(Seller.class));
    }

    @Test
    public void updateSeller_WhenSellerExistsAndActive_ShouldUpdatedSeller() {
        when(repository.existsById(MOCKED_ID)).thenReturn(true);
        when(repository.isActive(MOCKED_ID)).thenReturn(true);
        when(repository.getReferenceById(MOCKED_ID)).thenReturn(seller);
        when(repository.save(any(Seller.class))).thenReturn(seller);

        service.updateSeller(MOCKED_ID, updateRequest);

        verify(repository).existsById(MOCKED_ID);
        verify(repository).isActive(MOCKED_ID);
        verify(repository).getReferenceById(MOCKED_ID);
        verify(repository).save(seller);
    }

    @Test
    public void updateSeller_WhenSellerExistsAndActive_ShouldReturnUpdatedSeller() {
        when(repository.existsById(MOCKED_ID)).thenReturn(true);
        when(repository.isActive(MOCKED_ID)).thenReturn(true);
        when(repository.getReferenceById(MOCKED_ID)).thenReturn(seller);
        when(repository.save(any(Seller.class))).thenReturn(seller);

        Seller result = service.updateSeller(100L, updateRequest);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(updateRequest.getName());
        assertThat(result.getContactInfo()).isEqualTo(updateRequest.getContactInfo());

        verify(repository).save(seller);
    }

    @Test
    public void updateSeller_WhenSellerDoesNotExist_ShouldThrowNoSuchElementException() {
        when(repository.existsById(MOCKED_ID)).thenReturn(false);

        assertThatThrownBy(() -> service.updateSeller(100L, updateRequest))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(ExceptionMessages.SELLER_NOT_FOUND_MSG);

        verify(repository).existsById(MOCKED_ID);
        verify(repository, never()).isActive(MOCKED_ID);
        verify(repository, never()).save(any(Seller.class));
    }

    @Test
    public void updateSeller_WhenSellerExistsAndNonActive_ShouldThrowBadRequestException() {
        when(repository.existsById(MOCKED_ID)).thenReturn(true);
        when(repository.isActive(MOCKED_ID)).thenReturn(false);

        assertThatThrownBy(() -> service.updateSeller(100L, updateRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionMessages.ENTITY_DELETED_MSG);

        verify(repository).existsById(MOCKED_ID);
        verify(repository).isActive(MOCKED_ID);
        verify(repository, never()).save(any(Seller.class));
    }

    @Test
    public void deleteSeller_WhenSellerExistsAndActive_ShouldNotCallDelete() {
        when(repository.existsById(MOCKED_ID)).thenReturn(true);
        when(repository.isActive(MOCKED_ID)).thenReturn(true);
        when(repository.getReferenceById(MOCKED_ID)).thenReturn(seller);
        when(repository.save(any(Seller.class))).thenReturn(seller);

        service.deleteSeller(MOCKED_ID);

        verify(repository).save(seller);
        verify(repository, never()).delete(seller);
    }

    @Test
    public void deleteSeller_WhenSellerExistsAndActive_ShouldSetActiveToFalse() {
        when(repository.existsById(MOCKED_ID)).thenReturn(true);
        when(repository.isActive(MOCKED_ID)).thenReturn(true);
        when(repository.getReferenceById(MOCKED_ID)).thenReturn(seller);
        when(repository.save(any(Seller.class))).thenReturn(seller);

        service.deleteSeller(MOCKED_ID);

        assertThat(seller.isActive()).isFalse();
        verify(repository).existsById(MOCKED_ID);
        verify(repository).isActive(MOCKED_ID);
        verify(repository).getReferenceById(MOCKED_ID);
        verify(repository).save(seller);
        verify(repository, never()).delete(seller);
    }

    @Test
    public void deleteSeller_WhenSellerDoesNotExist_ShouldThrowNoSuchElementException() {
        when(repository.existsById(MOCKED_ID)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteSeller(MOCKED_ID))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(ExceptionMessages.SELLER_NOT_FOUND_MSG);

        verify(repository).existsById(MOCKED_ID);
        verify(repository, never()).save(any(Seller.class));
    }

    @Test
    void deleteSeller_WhenSellerExistsAlreadyDeleted_ShouldThrowBadRequestException() {
        when(repository.existsById(MOCKED_ID)).thenReturn(true);
        when(repository.isActive(MOCKED_ID)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteSeller(MOCKED_ID))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionMessages.ENTITY_DELETED_MSG);

        verify(repository).existsById(MOCKED_ID);
        verify(repository).isActive(MOCKED_ID);
        verify(repository, never()).save(any(Seller.class));
    }

    @Test
    public void retrieveAll_ShouldReturnPageOfActiveSellers() {
        Pageable expectedPageable = mock(Pageable.class);
        Page<Seller> expectedPage = new PageImpl<>(Collections.singletonList(seller));
        final String ORDER_BY = "name";

        pageableConverterMock.when(() -> PageableConverter.toPageableWithSortAsc(params, ORDER_BY))
                .thenReturn(expectedPageable);

        when(repository.findAllActive(expectedPageable)).thenReturn(expectedPage);

        Page<Seller> result = service.retrieveAll(params);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotNull();
        assertThat(result.getContent().getFirst()).isEqualTo(seller);

        pageableConverterMock.verify(() -> PageableConverter.toPageableWithSortAsc(params, ORDER_BY));
        verify(repository).findAllActive(expectedPageable);
    }
}
