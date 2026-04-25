import { request } from '../lib/request';

export interface RegisterParams {
  username: string;
  password: string;
  email: string;
  phone: string;
}

export interface VerifyParams {
  merchantId: string;
  code: string;
}

export interface LoginParams {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  username: string;
  role: string;
}

export const registerApi = (params: RegisterParams): Promise<string> => {
  return request('/auth/register', {
    method: 'POST',
    body: JSON.stringify(params),
  }) as Promise<string>;
};

export const verifyApi = (params: VerifyParams) => {
  return request<void>('/auth/verify', {
    method: 'POST',
    body: JSON.stringify(params),
  });
};

export const loginApi = (params: LoginParams) => {
  return request<LoginResponse>('/auth/login', {
    method: 'POST',
    body: JSON.stringify(params),
  });
};

export interface QualificationParams {
  merchantId: string;
  companyName: string;
  businessLicenseUrl: string;
  taxId: string;
  legalPerson: string;
  contactPhone: string;
}

export const submitQualificationApi = (params: QualificationParams) => {
  return request<void>('/qualification/submit', {
    method: 'POST',
    body: JSON.stringify(params),
  });
};

export const uploadLicenseApi = async (file: File, merchantId: string): Promise<string> => {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('merchantId', merchantId);

  const response = await fetch('/api/upload/license', {
    method: 'POST',
    body: formData,
  });

  const result = await response.json();
  if (result.code !== 200) {
    throw new Error(result.message || '上传失败');
  }
  return result.data;
};
