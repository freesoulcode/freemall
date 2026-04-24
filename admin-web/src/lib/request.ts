const BASE_URL = '/api';

export interface Result<T> {
  code: number;
  message: string;
  data: T;
}

export const request = async <T>(url: string, options: RequestInit = {}): Promise<T> => {
  const token = localStorage.getItem('token');
  const headers = new Headers(options.headers);
  
  if (token) {
    headers.set('Authorization', `Bearer ${token}`);
  }
  
  if (!(options.body instanceof FormData) && !headers.has('Content-Type')) {
    headers.set('Content-Type', 'application/json');
  }

  const response = await fetch(`${BASE_URL}${url}`, {
    ...options,
    headers,
  });

  const result: Result<T> = await response.json();

  if (result.code !== 200) {
    throw new Error(result.message || '请求失败');
  }

  return result.data;
};
