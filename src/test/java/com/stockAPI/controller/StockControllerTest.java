package com.stockAPI.controller;

import com.stockAPI.builder.ProductDTOBuilder;
import com.stockAPI.dto.request.ProductDTO;
import com.stockAPI.exception.ProductNotFoundException;
import com.stockAPI.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.stockAPI.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class StockControllerTest {

    private static final String STOCK_API_URL_PATH = "/api/v1/product";
    private static final Long VALID_PRODUCT_ID = 1L;
    private static final Long INVALID_PRODUCT_ID = 2L;

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private StockController stockController;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(stockController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenProductIsCreated() throws Exception {
        //given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        //when
        when(productService.createProduct(productDTO)).thenReturn(productDTO);

        //then
        mockMvc.perform(post(STOCK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(productDTO.getName())))
                .andExpect(jsonPath("$.brand", is(productDTO.getBrand())))
                .andExpect(jsonPath("$.type",is(productDTO.getType().toString())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        productDTO.setBrand(null);

        // then
        mockMvc.perform(post(STOCK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void whenGetIsCalledWithValidIdThenOkStatusIsReturned() throws Exception, ProductNotFoundException {
        //given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        //when
        when(productService.findById(productDTO.getId())).thenReturn(productDTO);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(STOCK_API_URL_PATH+"/"+productDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(productDTO.getName())))
                .andExpect(jsonPath("$.brand",is(productDTO.getBrand())))
                .andExpect(jsonPath("$.type",is(productDTO.getType().toString())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredIdThenNotFoundStatusIsReturned() throws Exception{
        // given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        //when
        when(productService.findById(productDTO.getId())).thenThrow(ProductNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(STOCK_API_URL_PATH + "/" + productDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithProductsIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        //when
        when(productService.listAll()).thenReturn(Collections.singletonList(productDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(STOCK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(productDTO.getName())))
                .andExpect(jsonPath("$[0].brand", is(productDTO.getBrand())))
                .andExpect(jsonPath("$[0].type", is(productDTO.getType().toString())));
    }

    @Test
    void whenGETListWithoutProductsIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        //when
        when(productService.listAll()).thenReturn(Collections.singletonList(productDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(STOCK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception{
        // given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        //when
        doNothing().when(productService).deleteById(productDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(STOCK_API_URL_PATH + "/" + productDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception{
        //when
        doThrow(ProductNotFoundException.class).when(productService).deleteById(INVALID_PRODUCT_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(STOCK_API_URL_PATH + "/" + INVALID_PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}

