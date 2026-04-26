import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { createProductApi, getProductDetailApi, updateProductApi } from '../api/product';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { useNavigate, useParams } from 'react-router-dom';

interface SkuForm {
  id?: string;
  name: string;
  price: string;
  stock: string;
}

const ProductFormPage: React.FC = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { productId } = useParams<{ productId: string }>();
  const isEdit = !!productId;

  const [name, setName] = useState('');
  const [subTitle, setSubTitle] = useState('');
  const [description, setDescription] = useState('');
  const [skus, setSkus] = useState<SkuForm[]>([{ name: '', price: '', stock: '' }]);
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [loading, setLoading] = useState(false);
  const [pageLoading, setPageLoading] = useState(isEdit);
  const [serverError, setServerError] = useState('');

  // Load existing product for edit mode
  useEffect(() => {
    if (isEdit && productId) {
      setPageLoading(true);
      getProductDetailApi(productId)
        .then(product => {
          setName(product.name);
          setSubTitle(product.subTitle || '');
          setDescription(product.description || '');
          if (product.skus && product.skus.length > 0) {
            setSkus(product.skus.map(sku => ({
              id: sku.id,
              name: sku.name,
              price: String(sku.price / 100),
              stock: String(sku.stock),
            })));
          }
        })
        .catch(err => {
          setServerError(err.message || t('product.loadError'));
        })
        .finally(() => setPageLoading(false));
    }
  }, [productId, isEdit, t]);

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
      if (isEdit) {
        await updateProductApi({
          id: productId!,
          name,
          subTitle,
          description,
          skus: skus.map(sku => ({
            id: sku.id,
            name: sku.name,
            price: Math.round(Number(sku.price) * 100),
            stock: Number(sku.stock),
          })),
        });
      } else {
        await createProductApi({
          name,
          subTitle,
          description,
          skus: skus.map(sku => ({
            name: sku.name,
            price: Math.round(Number(sku.price) * 100),
            stock: Number(sku.stock),
          })),
        });
      }
      navigate('/product/status');
    } catch (err: any) {
      setServerError(err.message || t('product.createError'));
    } finally {
      setLoading(false);
    }
  };

  if (pageLoading) {
    return <div className="flex justify-center items-center min-h-[400px]">{t('common.loading')}</div>;
  }

  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">{isEdit ? t('product.editTitle') : t('product.createTitle')}</h1>

      <form onSubmit={handleSubmit} className="space-y-6 bg-white p-6 rounded-lg shadow max-w-2xl">
        {serverError && (
          <div className="p-4 bg-red-50 text-red-700 rounded-md">{serverError}</div>
        )}

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">{t('product.name')}</label>
          <Input
            value={name}
            onChange={(e) => { setName(e.target.value); if (errors.name) setErrors({ ...errors, name: '' }); }}
            aria-invalid={!!errors.name}
          />
          {errors.name && <p className="text-xs text-red-500 mt-1">{errors.name}</p>}
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">{t('product.subTitle')}</label>
          <Input
            value={subTitle}
            onChange={(e) => setSubTitle(e.target.value)}
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">{t('product.description')}</label>
          <textarea
            className="w-full min-h-[80px] rounded-lg border border-input bg-transparent px-2.5 py-1 text-sm outline-none focus-visible:border-ring focus-visible:ring-3 focus-visible:ring-ring/50"
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
          <div className="space-y-3">
            {skus.map((sku, index) => (
              <div key={index} className="flex gap-2 items-start p-3 bg-gray-50 rounded-md">
                <div className="flex-1">
                  <Input
                    placeholder={t('product.skuName')}
                    value={sku.name}
                    onChange={(e) => handleSkuChange(index, 'name', e.target.value)}
                    aria-invalid={!!errors[`sku_${index}_name`]}
                  />
                  {errors[`sku_${index}_name`] && <p className="text-xs text-red-500 mt-1">{errors[`sku_${index}_name`]}</p>}
                </div>
                <div className="w-28">
                  <Input
                    placeholder={t('product.price')}
                    value={sku.price}
                    onChange={(e) => handleSkuChange(index, 'price', e.target.value)}
                    aria-invalid={!!errors[`sku_${index}_price`]}
                  />
                  {errors[`sku_${index}_price`] && <p className="text-xs text-red-500 mt-1">{errors[`sku_${index}_price`]}</p>}
                </div>
                <div className="w-28">
                  <Input
                    placeholder={t('product.stock')}
                    value={sku.stock}
                    onChange={(e) => handleSkuChange(index, 'stock', e.target.value)}
                    aria-invalid={!!errors[`sku_${index}_stock`]}
                  />
                  {errors[`sku_${index}_stock`] && <p className="text-xs text-red-500 mt-1">{errors[`sku_${index}_stock`]}</p>}
                </div>
                {skus.length > 1 && (
                  <Button type="button" size="icon-xs" variant="outline" onClick={() => removeSku(index)}>×</Button>
                )}
              </div>
            ))}
          </div>
        </div>

        <div className="flex gap-4 justify-end">
          <Button type="button" variant="outline" onClick={() => navigate('/product/status')}>{t('common.cancel')}</Button>
          <Button type="submit" disabled={loading}>{loading ? t('common.submitting') : t('common.submit')}</Button>
        </div>
      </form>
    </div>
  );
};

export default ProductFormPage;