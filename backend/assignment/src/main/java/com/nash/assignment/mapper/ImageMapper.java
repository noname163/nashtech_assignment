package com.nash.assignment.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.nash.assignment.dto.response.ImageAccountDto;
import com.nash.assignment.dto.response.ImageProductDto;
import com.nash.assignment.modal.Image;

@Component
public class ImageMapper {
    public ImageProductDto mapEntityToImageProductDto(Image image) {
        ImageProductDto result = new ImageProductDto();
        result.setProduct(image.getProduct());
        result.setUrl(image.getUrl());
        return result;
    }

    public Image mapImageProductDtoToEntity(ImageProductDto imageProductDto) {
        Image result = new Image();
        result.setAvatar(null);
        result.setProduct(imageProductDto.getProduct());
        result.setUrl(imageProductDto.getUrl());
        return result;
    }

    public Set<ImageProductDto> mapEntityToImageProductDto(Set<Image> imagesValue) {
        return imagesValue.stream().map(image -> mapEntityToImageProductDto(image)).collect(Collectors.toSet());
    }

    public Set<Image> mapImageProductDtoToEntity(Set<ImageProductDto> imagesValue) {
        return imagesValue.stream().map(image -> mapImageProductDtoToEntity(image))
                .collect(Collectors.toSet());
    }

    public ImageAccountDto mapEntityToImageAccountDto(Image image) {
        ImageAccountDto result = new ImageAccountDto();
        result.setAvatar(image.getAvatar());
        result.setUrl(image.getUrl());
        return result;
    }

    public Image mapImageAccountDtoToEntity(ImageAccountDto imageAccountDto) {
        Image result = new Image();
        result.setAvatar(imageAccountDto.getAvatar());
        result.setProduct(null);
        result.setUrl(imageAccountDto.getUrl());
        return result;
    }

}
