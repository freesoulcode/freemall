import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { getProductListApi, publishProductApi, offlineProductApi, deleteProductApi } from '../api/product';
import type { Product } from '../api/product';
import { Button } from '@/components/ui/button';
import { formatDateTime } from '@/lib/dateUtils';

interface ProductListPageProps {
  merchantId: string;
  onCreateNew: () => void;
}

const ProductListPage: React.FC<ProductListPageProps> = ({ merchantId, onCreateNew }) => {
  const { t, i18n } = useTranslation();
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const loadProducts = async () => {
    setLoading(true);
    setError('');
    try {
      const data = await getProductListApi(merchantId);
      setProducts(data || []);
    } catch (err: any) {
      setError(err.message || t('product.loadError'));
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadProducts();
  }, [merchantId]);

  const handlePublish = async (productId: string) => {
    try {
      await publishProductApi(productId);
      loadProducts();
    } catch (err: any) {
      setError(err.message);
    }
  };

  const handleOffline = async (productId: string) => {
    try {
      await offlineProductApi(productId);
      loadProducts();
    } catch (err: any) {
      setError(err.message);
    }
  };

  const handleDelete = async (productId: string) => {
    if (!window.confirm(t('product.confirmDelete'))) return;
    try {
      await deleteProductApi(productId);
      loadProducts();
    } catch (err: any) {
      setError(err.message);
    }
  };

  const getStatusBadge = (status: number) => {
    const styles: Record<number, string> = {
      0: 'bg-gray-100 text-gray-800',
      1: 'bg-yellow-100 text-yellow-800',
      2: 'bg-red-100 text-red-800',
      3: 'bg-blue-100 text-blue-800',
      4: 'bg-green-100 text-green-800',
      5: 'bg-gray-100 text-gray-800',
    };
    return styles[status] || 'bg-gray-100 text-gray-800';
  };

  if (loading) {
    return <div className="flex justify-center items-center min-h-screen">{t('common.loading')}</div>;
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">{t('product.title')}</h1>
        <Button onClick={onCreateNew}>{t('product.create')}</Button>
      </div>

      {error && (
        <div className="mb-4 p-4 bg-red-50 text-red-700 rounded-md">{error}</div>
      )}

      {products.length === 0 ? (
        <div className="text-center py-12 text-gray-500">
          {t('product.empty')}
        </div>
      ) : (
        <div className="bg-white rounded-lg shadow overflow-hidden">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">{t('product.name')}</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">{t('product.status')}</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">{t('product.skuCount')}</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">{t('product.createTime')}</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">{t('product.actions')}</th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {products.map((product) => (
                <tr key={product.id}>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="font-medium">{product.name}</div>
                    {product.subTitle && (
                      <div className="text-sm text-gray-500">{product.subTitle}</div>
                    )}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className={`px-2 py-1 text-xs rounded-full ${getStatusBadge(product.status)}`}>
                      {product.statusName}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {product.skus?.length || 0}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {formatDateTime(product.createTime, i18n.language)}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm space-x-2">
                    {product.status === 0 && (
                      <Button size="sm" onClick={() => handlePublish(product.id)}>
                        {t('product.publish')}
                      </Button>
                    )}
                    {product.status === 4 && (
                      <Button size="sm" variant="outline" onClick={() => handleOffline(product.id)}>
                        {t('product.offline')}
                      </Button>
                    )}
                    <Button size="sm" variant="destructive" onClick={() => handleDelete(product.id)}>
                      {t('product.delete')}
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default ProductListPage;
