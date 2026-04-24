package io.github.freesoulcode.product.domain.model;

import io.github.freesoulcode.common.interfaces.BusinessException;
import io.github.freesoulcode.product.domain.BizErrorCode;
import org.springframework.util.DigestUtils;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class SkuAttributes {
    private final Map<String, String> attributes;

    public SkuAttributes(Map<String, String> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            throw new BusinessException(BizErrorCode.PRODUCT_SKU_ATTRIBUTES_EMPTY);
        }
        this.attributes = Collections.unmodifiableMap(attributes);
    }

    public String generateHash() {
        String sorted = attributes.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + ":" + e.getValue())
                .collect(Collectors.joining("|"));
        return DigestUtils.md5DigestAsHex(sorted.getBytes());
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }
}
