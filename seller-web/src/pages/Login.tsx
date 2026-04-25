import React, { useState } from 'react';
import { loginApi } from '../api/merchant';
import { Button } from '@/components/ui/button';
import { useTranslation } from 'react-i18next';

interface LoginPageProps {
  onLoginSuccess: (token: string, username: string, merchantId: string) => void;
  onGoToRegister: () => void;
}

const LoginPage: React.FC<LoginPageProps> = ({ onLoginSuccess, onGoToRegister }) => {
  const { t } = useTranslation();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [loading, setLoading] = useState(false);
  const [serverError, setServerError] = useState('');

  const validate = () => {
    const newErrors: Record<string, string> = {};
    if (!username.trim()) newErrors.username = t('register.validation.usernameRequired');
    if (!password) newErrors.password = t('register.validation.passwordRequired');
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validate()) return;

    setLoading(true);
    setServerError('');

    try {
      const data = await loginApi({ username, password });
      onLoginSuccess(data.token, data.username, data.merchantId);
    } catch (err: any) {
      setServerError(err.message || t('login.error'));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-50 px-4 py-12 sm:px-6 lg:px-8">
      <div className="w-full max-w-md space-y-8 rounded-xl bg-white p-8 shadow-lg">
        <div>
          <h2 className="mt-6 text-center text-3xl font-bold tracking-tight text-gray-900">
            {t('login.title')}
          </h2>
          <p className="mt-2 text-center text-sm text-gray-600">
            {t('login.subtitle')}
          </p>
        </div>
        <form className="mt-8 space-y-6" onSubmit={handleSubmit} noValidate>
          {serverError && (
            <div className="rounded-md bg-red-50 p-4 text-sm text-red-700">
              {serverError}
            </div>
          )}
          <div className="space-y-4">
            <div>
              <label htmlFor="username" className="block text-sm font-medium text-gray-700">
                {t('login.username')}
              </label>
              <input
                id="username"
                name="username"
                type="text"
                className={`relative block w-full rounded-md border-0 py-1.5 px-3 text-gray-900 ring-1 ring-inset ${errors.username ? 'ring-red-500' : 'ring-gray-300'} placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-primary sm:text-sm sm:leading-6`}
                placeholder={t('login.usernamePlaceholder')}
                value={username}
                onChange={(e) => {
                  setUsername(e.target.value);
                  if (errors.username) setErrors({ ...errors, username: '' });
                }}
              />
              {errors.username && <p className="mt-1 text-xs text-red-500">{errors.username}</p>}
            </div>
            <div>
              <label htmlFor="password" title={t('login.password')} className="block text-sm font-medium text-gray-700">
                {t('login.password')}
              </label>
              <input
                id="password"
                name="password"
                type="password"
                className={`relative block w-full rounded-md border-0 py-1.5 px-3 text-gray-900 ring-1 ring-inset ${errors.password ? 'ring-red-500' : 'ring-gray-300'} placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-primary sm:text-sm sm:leading-6`}
                placeholder={t('login.passwordPlaceholder')}
                value={password}
                onChange={(e) => {
                  setPassword(e.target.value);
                  if (errors.password) setErrors({ ...errors, password: '' });
                }}
              />
              {errors.password && <p className="mt-1 text-xs text-red-500">{errors.password}</p>}
            </div>
          </div>

          <div>
            <Button
              type="submit"
              className="w-full"
              disabled={loading}
            >
              {loading ? t('login.submitting') : t('login.submit')}
            </Button>
          </div>

          <div className="text-center">
            <button
              type="button"
              onClick={onGoToRegister}
              className="text-sm font-medium text-primary hover:text-primary/80"
            >
              {t('login.noAccount')}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;
