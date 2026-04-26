import { request } from '../lib/request';

export interface Product {
  id: string;
  merchantId: string;
  name: string;
  subTitle: string;
  description: string;
  status: number;
  statusName: string;
  mainImage?: string;
  createTime?: string;
  updateTime?: string;
  skus: Sku[];
}

export interface Sku {
  id: string;
  productId: string;
  name: string;
  skuCode: string;
  price: number;
  costPrice: number;
  stock: number;
  specImg: string;
}

export interface PageResult<T> {
  records: T[];
  total: number;
  current: number;
  size: number;
  pages: number;
}

export interface CreateProductParams {
  name: string;
  subTitle?: string;
  description?: string;
  skus: {
    name: string;
    skuCode?: string;
    price: number;
    costPrice?: number;
    stock: number;
    specImg?: string;
  }[];
}

export interface UpdateProductParams {
  id: string;
  name: string;
  subTitle?: string;
  description?: string;
  skus?: {
    id?: string;
    name: string;
    skuCode?: string;
    price: number;
    costPrice?: number;
    stock: number;
    specImg?: string;
  }[];
}

export interface ProductSearchParams {
  name?: string;
  status?: number;
  categoryId?: number;
  page?: number;
  size?: number;
}

export const getProductListApi = (): Promise<Product[]> => {
  return request('/product/list');
};

export const searchProductsApi = (params: ProductSearchParams): Promise<PageResult<Product>> => {
  const query = new URLSearchParams();
  if (params.name) query.set('name', params.name);
  if (params.status !== undefined) query.set('status', String(params.status));
  if (params.categoryId) query.set('categoryId', String(params.categoryId));
  query.set('page', String(params.page || 1));
  query.set('size', String(params.size || 10));
  return request(`/product/search?${query.toString()}`);
};

export const getProductDetailApi = (productId: string): Promise<Product> => {
  return request(`/product/${productId}`);
};

export const createProductApi = (params: CreateProductParams): Promise<string> => {
  return request('/product', {
    method: 'POST',
    body: JSON.stringify(params),
  }) as Promise<string>;
};

export const updateProductApi = (params: UpdateProductParams): Promise<void> => {
  return request('/product', {
    method: 'PUT',
    body: JSON.stringify(params),
  });
};

export const publishProductApi = (productId: string): Promise<void> => {
  return request(`/product/${productId}/publish`, { method: 'PUT' });
};

export const offlineProductApi = (productId: string): Promise<void> => {
  return request(`/product/${productId}/offline`, { method: 'PUT' });
};

export const deleteProductApi = (productId: string): Promise<void> => {
  return request(`/product/${productId}`, { method: 'DELETE' });
};