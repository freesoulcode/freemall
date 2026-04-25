import { request } from '../lib/request';

export interface Product {
  id: string;
  merchantId: string;
  name: string;
  subTitle: string;
  description: string;
  status: number;
  statusName: string;
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

export interface CreateProductParams {
  merchantId: string;
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

export const getProductListApi = (merchantId: string): Promise<Product[]> => {
  return request(`/product/merchant/${merchantId}`);
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

export const updateProductApi = (params: { id: string; name: string; description?: string }): Promise<void> => {
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
