import { request } from '../lib/request';

export interface RegisterParams {
  username: string;
  password: string;
  email: string;
  phone: string;
}

export interface VerifyParams {
  merchantId: number;
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

export const registerApi = (params: RegisterParams) => {
  return request<number>('/auth/register', {
    method: 'POST',
    body: JSON.stringify(params),
  });
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
  merchantId: number;
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
