package io.github.freesoulcode.merchant.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.freesoulcode.merchant.domain.model.Merchant;
import io.github.freesoulcode.merchant.domain.model.MerchantQualification;
import io.github.freesoulcode.merchant.domain.model.MerchantStatus;
import io.github.freesoulcode.merchant.domain.repository.MerchantRepository;
import io.github.freesoulcode.merchant.infrastructure.persistence.entity.MerchantPO;
import io.github.freesoulcode.merchant.infrastructure.persistence.entity.MerchantQualificationPO;
import io.github.freesoulcode.merchant.infrastructure.persistence.mapper.MerchantMapper;
import io.github.freesoulcode.merchant.infrastructure.persistence.mapper.MerchantQualificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MerchantRepositoryImpl implements MerchantRepository {

    private final MerchantMapper merchantMapper;
    private final MerchantQualificationMapper qualificationMapper;

    @Override
    public Optional<Merchant> findById(Long id) {
        MerchantPO po = merchantMapper.selectById(id);
        if (po == null) {
            return Optional.empty();
        }
        
        MerchantQualificationPO qPo = qualificationMapper.selectOne(
                new LambdaQueryWrapper<MerchantQualificationPO>().eq(MerchantQualificationPO::getMerchantId, id)
        );
        
        return Optional.of(convertToDomain(po, qPo));
    }

    @Override
    public Optional<Merchant> findByUsername(String username) {
        MerchantPO po = merchantMapper.selectOne(
                new LambdaQueryWrapper<MerchantPO>().eq(MerchantPO::getUsername, username)
        );
        if (po == null) {
            return Optional.empty();
        }
        return findById(po.getId());
    }

    @Override
    @Transactional
    public void save(Merchant merchant) {
        MerchantPO po = convertToPO(merchant);
        if (po.getId() == null) {
            merchantMapper.insert(po);
            merchant.setId(po.getId());
        } else {
            merchantMapper.updateById(po);
        }

        if (merchant.getQualification() != null) {
            MerchantQualificationPO qPo = convertToPO(merchant.getQualification(), merchant.getId());
            if (qPo.getId() == null) {
                qualificationMapper.insert(qPo);
                merchant.getQualification().setId(qPo.getId());
            } else {
                qualificationMapper.updateById(qPo);
            }
        }
    }

    private Merchant convertToDomain(MerchantPO po, MerchantQualificationPO qPo) {
        Merchant.MerchantBuilder builder = Merchant.builder()
                .id(po.getId())
                .username(po.getUsername())
                .password(po.getPassword())
                .email(po.getEmail())
                .phone(po.getPhone())
                .status(MerchantStatus.fromCode(po.getStatus()))
                .createTime(po.getCreateTime())
                .updateTime(po.getUpdateTime());
        
        if (qPo != null) {
            builder.qualification(MerchantQualification.builder()
                    .id(qPo.getId())
                    .merchantId(qPo.getMerchantId())
                    .companyName(qPo.getCompanyName())
                    .businessLicenseUrl(qPo.getBusinessLicenseUrl())
                    .taxId(qPo.getTaxId())
                    .legalPerson(qPo.getLegalPerson())
                    .contactPhone(qPo.getContactPhone())
                    .build());
        }
        
        return builder.build();
    }

    private MerchantPO convertToPO(Merchant domain) {
        MerchantPO po = new MerchantPO();
        po.setId(domain.getId());
        po.setUsername(domain.getUsername());
        po.setPassword(domain.getPassword());
        po.setEmail(domain.getEmail());
        po.setPhone(domain.getPhone());
        po.setStatus(domain.getStatus().getCode());
        po.setCreateTime(domain.getCreateTime());
        po.setUpdateTime(domain.getUpdateTime());
        return po;
    }

    private MerchantQualificationPO convertToPO(MerchantQualification domain, Long merchantId) {
        MerchantQualificationPO po = new MerchantQualificationPO();
        po.setId(domain.getId());
        po.setMerchantId(merchantId);
        po.setCompanyName(domain.getCompanyName());
        po.setBusinessLicenseUrl(domain.getBusinessLicenseUrl());
        po.setTaxId(domain.getTaxId());
        po.setLegalPerson(domain.getLegalPerson());
        po.setContactPhone(domain.getContactPhone());
        return po;
    }
}
