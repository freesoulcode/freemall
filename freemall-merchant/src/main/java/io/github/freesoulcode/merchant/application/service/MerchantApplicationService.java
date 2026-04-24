package io.github.freesoulcode.merchant.application.service;

import io.github.freesoulcode.common.interfaces.BusinessException;
import io.github.freesoulcode.common.interfaces.SystemErrorCode;
import io.github.freesoulcode.merchant.application.request.MerchantQualificationRequest;
import io.github.freesoulcode.merchant.application.request.MerchantRegisterRequest;
import io.github.freesoulcode.merchant.application.request.MerchantVerifyRequest;
import io.github.freesoulcode.merchant.domain.model.Merchant;
import io.github.freesoulcode.merchant.domain.model.MerchantQualification;
import io.github.freesoulcode.merchant.domain.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.freesoulcode.merchant.application.response.MerchantAuthResponse;

@Service
@RequiredArgsConstructor
public class MerchantApplicationService {

    private final MerchantRepository merchantRepository;

    public MerchantAuthResponse getAuthInfo(String username) {
        return merchantRepository.findByUsername(username)
                .map(m -> MerchantAuthResponse.builder()
                        .id(m.getId())
                        .username(m.getUsername())
                        .password(m.getPassword())
                        .status(m.getStatus().getCode())
                        .build())
                .orElse(null);
    }

    @Transactional
    public Long register(MerchantRegisterRequest request) {
        // 1. 检查用户名是否已存在
        if (merchantRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BusinessException(SystemErrorCode.PARAM_ERROR, "用户名已存在");
        }

        // 2. 使用领域模型创建商家
        Merchant merchant = Merchant.create(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getPhone()
        );
        
        // 3. 通过仓储保存
        merchantRepository.save(merchant);
        
        return merchant.getId();
    }

    @Transactional
    public void verify(MerchantVerifyRequest request) {
        // 1. 加载聚合根
        Merchant merchant = merchantRepository.findById(request.getMerchantId())
                .orElseThrow(() -> new BusinessException(SystemErrorCode.NOT_FOUND, "商家不存在"));

        // 2. 执行领域逻辑
        merchant.verify(request.getCode());

        // 3. 保存变更
        merchantRepository.save(merchant);
    }

    @Transactional
    public void submitQualification(MerchantQualificationRequest request) {
        // 1. 加载聚合根
        Merchant merchant = merchantRepository.findById(request.getMerchantId())
                .orElseThrow(() -> new BusinessException(SystemErrorCode.NOT_FOUND, "商家不存在"));

        // 2. 构造领域对象
        MerchantQualification qualification = MerchantQualification.builder()
                .merchantId(request.getMerchantId())
                .companyName(request.getCompanyName())
                .businessLicenseUrl(request.getBusinessLicenseUrl())
                .taxId(request.getTaxId())
                .legalPerson(request.getLegalPerson())
                .contactPhone(request.getContactPhone())
                .build();

        // 3. 执行领域逻辑
        merchant.submitQualification(qualification);

        // 4. 保存变更
        merchantRepository.save(merchant);
    }
}
