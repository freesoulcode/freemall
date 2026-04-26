import React, { useState, useEffect, useCallback } from 'react';
import { useTranslation } from 'react-i18next';
import { searchProductsApi, publishProductApi, offlineProductApi, deleteProductApi } from '../api/product';
import type { Product, ProductSearchParams } from '../api/product';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { formatDateTime } from '@/lib/dateUtils';
import { useNavigate } from 'react-router-dom';
import { Pencil, ArrowUp, ArrowDown, Trash2, Search, RotateCcw, Package } from 'lucide-react';

const STATUS_OPTIONS = [
  { value: -1, labelKey: 'product.statusAll' },
  { value: 0, labelKey: 'product.statusDraft' },
  { value: 1, labelKey: 'product.statusPendingAudit' },
  { value: 4, labelKey: 'product.statusOnShelf' },
  { value: 5, labelKey: 'product.statusOffShelf' },
  { value: 6, labelKey: 'product.statusSoldOut' },
];

const ProductListPage: React.FC = () => {
  const { t, i18n } = useTranslation();
  const navigate = useNavigate();

  // Filter state
  const [filterName, setFilterName] = useState('');
  const [filterStatus, setFilterStatus] = useState(-1);

  // Table state
  const [products, setProducts] = useState<Product[]>([]);
  const [total, setTotal] = useState(0);
  const [page, setPage] = useState(1);
  const [size] = useState(10);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  // Batch selection
  const [selectedIds, setSelectedIds] = useState<Set<string>>(new Set());

  const loadProducts = useCallback(async () => {
    setLoading(true);
    setError('');
    try {
      const params: ProductSearchParams = {
        page,
        size,
      };
      if (filterName.trim()) params.name = filterName.trim();
      if (filterStatus >= 0) params.status = filterStatus;

      const result = await searchProductsApi(params);
      setProducts(result.records || []);
      setTotal(result.total || 0);
    } catch (err: any) {
      setError(err.message || t('product.loadError'));
    } finally {
      setLoading(false);
    }
  }, [page, size, filterName, filterStatus, t]);

  useEffect(() => {
    loadProducts();
  }, [loadProducts]);

  // Reset filters
  const handleReset = () => {
    setFilterName('');
    setFilterStatus(-1);
    setPage(1);
    setSelectedIds(new Set());
  };

  // Search
  const handleSearch = () => {
    setPage(1);
    setSelectedIds(new Set());
    loadProducts();
  };

  // Actions
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

  // Batch publish
  const handleBatchPublish = async () => {
    if (selectedIds.size === 0) return;
    try {
      for (const id of selectedIds) {
        await publishProductApi(id);
      }
      setSelectedIds(new Set());
      loadProducts();
    } catch (err: any) {
      setError(err.message);
    }
  };

  // Batch offline
  const handleBatchOffline = async () => {
    if (selectedIds.size === 0) return;
    try {
      for (const id of selectedIds) {
        await offlineProductApi(id);
      }
      setSelectedIds(new Set());
      loadProducts();
    } catch (err: any) {
      setError(err.message);
    }
  };

  // Batch delete
  const handleBatchDelete = async () => {
    if (selectedIds.size === 0) return;
    if (!window.confirm(t('product.confirmBatchDelete'))) return;
    try {
      for (const id of selectedIds) {
        await deleteProductApi(id);
      }
      setSelectedIds(new Set());
      loadProducts();
    } catch (err: any) {
      setError(err.message);
    }
  };

  // Select/deselect all
  const handleSelectAll = () => {
    if (selectedIds.size === products.length) {
      setSelectedIds(new Set());
    } else {
      setSelectedIds(new Set(products.map(p => p.id)));
    }
  };

  const handleSelectItem = (id: string) => {
    const next = new Set(selectedIds);
    if (next.has(id)) {
      next.delete(id);
    } else {
      next.add(id);
    }
    setSelectedIds(next);
  };

  // Format price (cents to yuan)
  const formatPrice = (skus: Product['skus']) => {
    if (!skus || skus.length === 0) return '—';
    const minPrice = Math.min(...skus.map(s => s.price));
    return `¥${(minPrice / 100).toFixed(2)}`;
  };

  // Total stock
  const totalStock = (skus: Product['skus']) => {
    if (!skus || skus.length === 0) return 0;
    return skus.reduce((sum, s) => sum + s.stock, 0);
  };

  // Status badge
  const getStatusBadge = (status: number) => {
    const styles: Record<number, string> = {
      0: 'bg-gray-100 text-gray-700',
      1: 'bg-yellow-100 text-yellow-700',
      2: 'bg-red-100 text-red-700',
      3: 'bg-blue-100 text-blue-700',
      4: 'bg-green-100 text-green-700',
      5: 'bg-gray-200 text-gray-600',
      6: 'bg-orange-100 text-orange-700',
      7: 'bg-purple-100 text-purple-700',
    };
    return styles[status] || 'bg-gray-100 text-gray-700';
  };

  // Pagination
  const totalPages = Math.ceil(total / size);
  const pageNumbers = [];
  const startPage = Math.max(1, page - 2);
  const endPage = Math.min(totalPages, page + 2);
  for (let i = startPage; i <= endPage; i++) {
    pageNumbers.push(i);
  }

  return (
    <div className="space-y-4">
      {/* Header */}
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold">{t('product.productList')}</h1>
        <Button onClick={() => navigate('/product/publish')}>
          <Package className="size-4 mr-1" />
          {t('product.create')}
        </Button>
      </div>

      {/* Filter Area */}
      <div className="bg-white p-4 rounded-lg shadow">
        <div className="flex items-center gap-4 flex-wrap">
          <div className="flex items-center gap-2">
            <label className="text-sm font-medium text-gray-700 whitespace-nowrap">{t('product.name')}:</label>
            <Input
              value={filterName}
              onChange={(e) => setFilterName(e.target.value)}
              placeholder={t('product.namePlaceholder')}
              className="w-48"
            />
          </div>
          <div className="flex items-center gap-2">
            <label className="text-sm font-medium text-gray-700 whitespace-nowrap">{t('product.status')}:</label>
            <select
              value={filterStatus}
              onChange={(e) => setFilterStatus(Number(e.target.value))}
              className="h-8 rounded-lg border border-input bg-transparent px-2.5 py-1 text-sm outline-none focus-visible:border-ring focus-visible:ring-3 focus-visible:ring-ring/50"
            >
              {STATUS_OPTIONS.map(opt => (
                <option key={opt.value} value={opt.value}>{t(opt.labelKey)}</option>
              ))}
            </select>
          </div>
          <div className="flex gap-2">
            <Button size="sm" onClick={handleSearch}>
              <Search className="size-3.5 mr-1" />
              {t('product.search')}
            </Button>
            <Button size="sm" variant="outline" onClick={handleReset}>
              <RotateCcw className="size-3.5 mr-1" />
              {t('product.reset')}
            </Button>
          </div>
        </div>
      </div>

      {/* Error */}
      {error && (
        <div className="p-4 bg-red-50 text-red-700 rounded-md">{error}</div>
      )}

      {/* Batch Operations */}
      {selectedIds.size > 0 && (
        <div className="bg-white p-3 rounded-lg shadow flex items-center gap-3">
          <span className="text-sm text-gray-600">{t('product.selectedCount', { count: selectedIds.size })}</span>
          <Button size="sm" variant="outline" onClick={handleBatchPublish}>
            <ArrowUp className="size-3.5 mr-1" />
            {t('product.batchPublish')}
          </Button>
          <Button size="sm" variant="outline" onClick={handleBatchOffline}>
            <ArrowDown className="size-3.5 mr-1" />
            {t('product.batchOffline')}
          </Button>
          <Button size="sm" variant="destructive" onClick={handleBatchDelete}>
            <Trash2 className="size-3.5 mr-1" />
            {t('product.batchDelete')}
          </Button>
        </div>
      )}

      {/* Table */}
      <div className="bg-white rounded-lg shadow overflow-hidden">
        {loading ? (
          <div className="flex justify-center items-center min-h-[300px]">{t('common.loading')}</div>
        ) : products.length === 0 ? (
          <div className="text-center py-12 text-gray-500">{t('product.empty')}</div>
        ) : (
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-4 py-3 text-left w-10">
                  <input
                    type="checkbox"
                    checked={selectedIds.size === products.length && products.length > 0}
                    onChange={handleSelectAll}
                    className="rounded border-gray-300"
                  />
                </th>
                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">{t('product.productInfo')}</th>
                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">{t('product.priceYuan')}</th>
                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">{t('product.stock')}</th>
                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">{t('product.status')}</th>
                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">{t('product.createTime')}</th>
                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">{t('product.actions')}</th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {products.map((product) => (
                <tr key={product.id} className={selectedIds.has(product.id) ? 'bg-blue-50' : ''}>
                  <td className="px-4 py-4">
                    <input
                      type="checkbox"
                      checked={selectedIds.has(product.id)}
                      onChange={() => handleSelectItem(product.id)}
                      className="rounded border-gray-300"
                    />
                  </td>
                  <td className="px-4 py-4">
                    <div className="flex items-center gap-3">
                      {product.mainImage ? (
                        <img src={product.mainImage} alt="" className="w-10 h-10 rounded object-cover bg-gray-100" />
                      ) : (
                        <div className="w-10 h-10 rounded bg-gray-100 flex items-center justify-center">
                          <Package className="size-4 text-gray-400" />
                        </div>
                      )}
                      <div>
                        <div className="font-medium text-sm">{product.name}</div>
                        {product.subTitle && (
                          <div className="text-xs text-gray-500">{product.subTitle}</div>
                        )}
                        <div className="text-xs text-gray-400 mt-0.5">ID: {product.id}</div>
                      </div>
                    </div>
                  </td>
                  <td className="px-4 py-4 text-sm">{formatPrice(product.skus)}</td>
                  <td className="px-4 py-4 text-sm">
                    {totalStock(product.skus) === 0 ? (
                      <span className="text-red-600">{t('product.statusSoldOut')}</span>
                    ) : totalStock(product.skus)}
                  </td>
                  <td className="px-4 py-4">
                    <span className={`px-2 py-0.5 text-xs rounded-full ${getStatusBadge(product.status)}`}>
                      {product.statusName}
                    </span>
                  </td>
                  <td className="px-4 py-4 text-sm text-gray-500 whitespace-nowrap">
                    {formatDateTime(product.createTime, i18n.language)}
                  </td>
                  <td className="px-4 py-4 text-sm">
                    <div className="flex gap-1">
                      <Button size="xs" variant="ghost" onClick={() => navigate(`/product/edit/${product.id}`)}>
                        <Pencil className="size-3" />
                      </Button>
                      {(product.status === 0 || product.status === 5) && (
                        <Button size="xs" variant="ghost" onClick={() => handlePublish(product.id)}>
                          <ArrowUp className="size-3" />
                        </Button>
                      )}
                      {product.status === 4 && (
                        <Button size="xs" variant="ghost" onClick={() => handleOffline(product.id)}>
                          <ArrowDown className="size-3" />
                        </Button>
                      )}
                      <Button size="xs" variant="ghost" onClick={() => handleDelete(product.id)}>
                        <Trash2 className="size-3 text-red-500" />
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      {/* Pagination */}
      {totalPages > 0 && (
        <div className="flex items-center justify-between bg-white p-3 rounded-lg shadow">
          <div className="text-sm text-gray-600">
            {t('product.totalCount', { count: total })}
          </div>
          <div className="flex items-center gap-2">
            <Button
              size="xs"
              variant="outline"
              disabled={page <= 1}
              onClick={() => setPage(page - 1)}
            >
              {t('product.prevPage')}
            </Button>
            {pageNumbers.map(num => (
              <Button
                key={num}
                size="xs"
                variant={num === page ? 'default' : 'outline'}
                onClick={() => setPage(num)}
              >
                {num}
              </Button>
            ))}
            {totalPages > endPage && <span className="text-gray-400">...</span>}
            {totalPages > endPage && (
              <Button size="xs" variant="outline" onClick={() => setPage(totalPages)}>
                {totalPages}
              </Button>
            )}
            <Button
              size="xs"
              variant="outline"
              disabled={page >= totalPages}
              onClick={() => setPage(page + 1)}
            >
              {t('product.nextPage')}
            </Button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ProductListPage;