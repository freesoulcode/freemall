import { request } from '../lib/request';

export interface LoginParams {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  username: string;
  role: string;
}

export const loginApi = (params: LoginParams) => {
  return request<LoginResponse>('/auth/login', {
    method: 'POST',
    body: JSON.stringify(params),
  });
};

export const logoutApi = () => {
  return request<void>('/auth/logout', {
    method: 'POST',
  });
};
