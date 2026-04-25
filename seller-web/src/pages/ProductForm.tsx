import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { createProductApi } from '../api/product';
import { Button } from '@/components/ui/button';

interface ProductFormPageProps {
  merchantId: string;
  onSuccess: () => void;
  onCancel: () => void;
}

interface SkuForm {
  name: string;
  price: string;
  stock: string;
}

const ProductFormPage: React.FC<ProductFormPageProps> = ({ merchantId, onSuccess, onCancel }) => {
  const { t } = useTranslation();
  const [name, setName] = useState('');
  const [subTitle, setSubTitle] = useState('');
  const [description, setDescription] = useState('');
  const [skus, setSkus] = useState<SkuForm[]>([{ name: '', price: '', stock: '' }]);
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [loading, setLoading] = useState(false);
  const [serverError, setServerError] = useState('');

  const validate = () => {
    const newErrors: Record<string, string> = {};
    if (!name.trim()) newErrors.name = t('product.validation.nameRequired');
    if (skus.length === 0) newErrors.skus = t('product.validation.skuRequired');
    skus.forEach((sku, index) => {
      if (!sku.name.trim()) newErrors[`sku_${index}_name`] = t('product.validation.skuNameRequired');
      if (!sku.price || isNaN(Number(sku.price))) newErrors[`sku_${index}_price`] = t('product.validation.priceRequired');
      if (!sku.stock || isNaN(Number(sku.stock))) newErrors[`sku_${index}_stock`] = t('product.validation.stockRequired');
    });
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSkuChange = (index: number, field: keyof SkuForm, value: string) => {
    const newSkus = [...skus];
    newSkus[index][field] = value;
    setSkus(newSkus);
    const key = `sku_${index}_${field}`;
    if (errors[key]) setErrors({ ...errors, [key]: '' });
  };

  const addSku = () => {
    setSkus([...skus, { name: '', price: '', stock: '' }]);
  };

  const removeSku = (index: number) => {
    if (skus.length > 1) {
      setSkus(skus.filter((_, i) => i !== index));
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validate()) return;

    setLoading(true);
    setServerError('');

    try {
      await createProductApi({
        merchantId,
        name,
        subTitle,
        description,
        skus: skus.map(sku => ({
          name: sku.name,
          price: Math.round(Number(sku.price) * 100),
          stock: Number(sku.stock),
        })),
      });
      onSuccess();
    } catch (err: any) {
      setServerError(err.message || t('product.createError'));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mx-auto px-4 py-8 max-w-2xl">
      <h1 className="text-2xl font-bold mb-6">{t('product.createTitle')}</h1>

      <form onSubmit={handleSubmit} className="space-y-6 bg-white p-6 rounded-lg shadow">
        {serverError && (
          <div className="p-4 bg-red-50 text-red-700 rounded-md">{serverError}</div>
        )}

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">{t('product.name')}</label>
          <input
            type="text"
            className={`w-full rounded-md border-0 py-1.5 px-3 ring-1 ${errors.name ? 'ring-red-500' : 'ring-gray-300'}`}
            value={name}
            onChange={(e) => { setName(e.target.value); if (errors.name) setErrors({ ...errors, name: '' }); }}
          />
          {errors.name && <p className="text-xs text-red-500 mt-1">{errors.name}</p>}
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">{t('product.subTitle')}</label>
          <input
            type="text"
            className="w-full rounded-md border-0 py-1.5 px-3 ring-1 ring-gray-300"
            value={subTitle}
            onChange={(e) => setSubTitle(e.target.value)}
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">{t('product.description')}</label>
          <textarea
            className="w-full rounded-md border-0 py-1.5 px-3 ring-1 ring-gray-300"
            rows={3}
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </div>

        <div>
          <div className="flex justify-between items-center mb-2">
            <label className="block text-sm font-medium text-gray-700">{t('product.skuList')}</label>
            <Button type="button" size="sm" onClick={addSku}>{t('product.addSku')}</Button>
          </div>
          {errors.skus && <p className="text-xs text-red-500 mb-2">{errors.skus}</p>}
          <div className="space-y-4">
            {skus.map((sku, index) => (
              <div key={index} className="flex gap-2 items-start p-3 bg-gray-50 rounded-md">
                <div className="flex-1">
                  <input
                    type="text"
                    placeholder={t('product.skuName')}
                    className={`w-full rounded-md border-0 py-1.5 px-2 ring-1 ${errors[`sku_${index}_name`] ? 'ring-red-500' : 'ring-gray-300'}`}
                    value={sku.name}
                    onChange={(e) => handleSkuChange(index, 'name', e.target.value)}
                  />
                  {errors[`sku_${index}_name`] && <p className="text-xs text-red-500 mt-1">{errors[`sku_${index}_name`]}</p>}
                </div>
                <div className="w-24">
                  <input
                    type="text"
                    placeholder={t('product.price')}
                    className={`w-full rounded-md border-0 py-1.5 px-2 ring-1 ${errors[`sku_${index}_price`] ? 'ring-red-500' : 'ring-gray-300'}`}
                    value={sku.price}
                    onChange={(e) => handleSkuChange(index, 'price', e.target.value)}
                  />
                  {errors[`sku_${index}_price`] && <p className="text-xs text-red-500 mt-1">{errors[`sku_${index}_price`]}</p>}
                </div>
                <div className="w-24">
                  <input
                    type="text"
                    placeholder={t('product.stock')}
                    className={`w-full rounded-md border-0 py-1.5 px-2 ring-1 ${errors[`sku_${index}_stock`] ? 'ring-red-500' : 'ring-gray-300'}`}
                    value={sku.stock}
                    onChange={(e) => handleSkuChange(index, 'stock', e.target.value)}
                  />
                  {errors[`sku_${index}_stock`] && <p className="text-xs text-red-500 mt-1">{errors[`sku_${index}_stock`]}</p>}
                </div>
                {skus.length > 1 && (
                  <Button type="button" size="sm" variant="outline" onClick={() => removeSku(index)}>×</Button>
                )}
              </div>
            ))}
          </div>
        </div>

        <div className="flex gap-4 justify-end">
          <Button type="button" variant="outline" onClick={onCancel}>{t('common.cancel')}</Button>
          <Button type="submit" disabled={loading}>{loading ? t('common.submitting') : t('common.submit')}</Button>
        </div>
      </form>
    </div>
  );
};

export default ProductFormPage;
