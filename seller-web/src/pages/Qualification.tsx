import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { useTranslation } from 'react-i18next';
import { submitQualificationApi } from '../api/merchant';

interface QualificationPageProps {
  merchantId: number;
  onSubmitSuccess: () => void;
}

interface QualificationFormData {
  companyName: string;
  businessLicenseUrl: string;
  taxId: string;
  legalPerson: string;
  contactPhone: string;
}

const QualificationPage: React.FC<QualificationPageProps> = ({ merchantId, onSubmitSuccess }) => {
  const { t } = useTranslation();
  const [formData, setFormData] = useState<QualificationFormData>({
    companyName: '',
    businessLicenseUrl: '',
    taxId: '',
    legalPerson: '',
    contactPhone: '',
  });
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [loading, setLoading] = useState(false);
  const [serverError, setServerError] = useState('');

  const validate = () => {
    const newErrors: Record<string, string> = {};
    
    if (!formData.companyName.trim()) {
      newErrors.companyName = t('qualification.validation.companyNameRequired');
    }
    
    if (!formData.businessLicenseUrl.trim()) {
      newErrors.businessLicenseUrl = t('qualification.validation.businessLicenseRequired');
    } else if (!/^https?:\/\/.+/.test(formData.businessLicenseUrl)) {
      newErrors.businessLicenseUrl = t('qualification.validation.businessLicenseInvalid');
    }
    
    if (!formData.taxId.trim()) {
      newErrors.taxId = t('qualification.validation.taxIdRequired');
    } else if (!/^[0-9A-Z]{15,20}$/.test(formData.taxId)) {
      newErrors.taxId = t('qualification.validation.taxIdInvalid');
    }
    
    if (!formData.legalPerson.trim()) {
      newErrors.legalPerson = t('qualification.validation.legalPersonRequired');
    }
    
    if (!formData.contactPhone.trim()) {
      newErrors.contactPhone = t('qualification.validation.contactPhoneRequired');
    } else if (!/^\d{10,15}$/.test(formData.contactPhone)) {
      newErrors.contactPhone = t('qualification.validation.contactPhoneInvalid');
    }
    
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    if (errors[name]) {
      setErrors({ ...errors, [name]: '' });
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validate()) return;
    
    setLoading(true);
    setServerError('');
    
    try {
      await submitQualificationApi({
        merchantId,
        ...formData,
      });
      onSubmitSuccess();
    } catch (err: any) {
      setServerError(err.message || t('qualification.error'));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-50 px-4 py-12 sm:px-6 lg:px-8">
      <div className="w-full max-w-lg space-y-8 rounded-xl bg-white p-8 shadow-lg">
        <div>
          <h2 className="mt-6 text-center text-3xl font-bold tracking-tight text-gray-900">
            {t('qualification.title')}
          </h2>
          <p className="mt-2 text-center text-sm text-gray-600">
            {t('qualification.subtitle')}
          </p>
        </div>
        <form className="mt-8 space-y-6" onSubmit={handleSubmit} noValidate>
          {serverError && (
            <div className="rounded-md bg-red-50 p-4 text-sm text-red-700">
              {serverError}
            </div>
          )}
          
          <div className="space-y-4">
            <div>
              <label htmlFor="companyName" className="block text-sm font-medium text-gray-700">
                {t('qualification.companyName')}
              </label>
              <input
                id="companyName"
                name="companyName"
                type="text"
                className={`mt-1 block w-full rounded-md border-0 py-1.5 px-3 text-gray-900 ring-1 ring-inset ${errors.companyName ? 'ring-red-500' : 'ring-gray-300'} placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-primary sm:text-sm sm:leading-6`}
                placeholder={t('qualification.companyNamePlaceholder')}
                value={formData.companyName}
                onChange={handleChange}
              />
              {errors.companyName && <p className="mt-1 text-xs text-red-500">{errors.companyName}</p>}
            </div>
            
            <div>
              <label htmlFor="businessLicenseUrl" className="block text-sm font-medium text-gray-700">
                {t('qualification.businessLicenseUrl')}
              </label>
              <input
                id="businessLicenseUrl"
                name="businessLicenseUrl"
                type="url"
                className={`mt-1 block w-full rounded-md border-0 py-1.5 px-3 text-gray-900 ring-1 ring-inset ${errors.businessLicenseUrl ? 'ring-red-500' : 'ring-gray-300'} placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-primary sm:text-sm sm:leading-6`}
                placeholder={t('qualification.businessLicensePlaceholder')}
                value={formData.businessLicenseUrl}
                onChange={handleChange}
              />
              {errors.businessLicenseUrl && <p className="mt-1 text-xs text-red-500">{errors.businessLicenseUrl}</p>}
              <p className="mt-1 text-xs text-gray-400">{t('qualification.businessLicenseHint')}</p>
            </div>
            
            <div>
              <label htmlFor="taxId" className="block text-sm font-medium text-gray-700">
                {t('qualification.taxId')}
              </label>
              <input
                id="taxId"
                name="taxId"
                type="text"
                className={`mt-1 block w-full rounded-md border-0 py-1.5 px-3 text-gray-900 ring-1 ring-inset ${errors.taxId ? 'ring-red-500' : 'ring-gray-300'} placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-primary sm:text-sm sm:leading-6`}
                placeholder={t('qualification.taxIdPlaceholder')}
                value={formData.taxId}
                onChange={handleChange}
              />
              {errors.taxId && <p className="mt-1 text-xs text-red-500">{errors.taxId}</p>}
            </div>
            
            <div>
              <label htmlFor="legalPerson" className="block text-sm font-medium text-gray-700">
                {t('qualification.legalPerson')}
              </label>
              <input
                id="legalPerson"
                name="legalPerson"
                type="text"
                className={`mt-1 block w-full rounded-md border-0 py-1.5 px-3 text-gray-900 ring-1 ring-inset ${errors.legalPerson ? 'ring-red-500' : 'ring-gray-300'} placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-primary sm:text-sm sm:leading-6`}
                placeholder={t('qualification.legalPersonPlaceholder')}
                value={formData.legalPerson}
                onChange={handleChange}
              />
              {errors.legalPerson && <p className="mt-1 text-xs text-red-500">{errors.legalPerson}</p>}
            </div>
            
            <div>
              <label htmlFor="contactPhone" className="block text-sm font-medium text-gray-700">
                {t('qualification.contactPhone')}
              </label>
              <input
                id="contactPhone"
                name="contactPhone"
                type="text"
                className={`mt-1 block w-full rounded-md border-0 py-1.5 px-3 text-gray-900 ring-1 ring-inset ${errors.contactPhone ? 'ring-red-500' : 'ring-gray-300'} placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-primary sm:text-sm sm:leading-6`}
                placeholder={t('qualification.contactPhonePlaceholder')}
                value={formData.contactPhone}
                onChange={handleChange}
              />
              {errors.contactPhone && <p className="mt-1 text-xs text-red-500">{errors.contactPhone}</p>}
            </div>
          </div>

          <div>
            <Button
              type="submit"
              className="w-full"
              disabled={loading}
            >
              {loading ? t('qualification.submitting') : t('qualification.submit')}
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default QualificationPage;
