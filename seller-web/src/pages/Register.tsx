import React, { useState } from 'react';
import { registerApi } from '../api/merchant';
import { Button } from '@/components/ui/button';
import { useTranslation } from 'react-i18next';

interface RegisterPageProps {
  onRegisterSuccess: (merchantId: number) => void;
  onGoToLogin: () => void;
}

const RegisterPage: React.FC<RegisterPageProps> = ({ onRegisterSuccess, onGoToLogin }) => {
  const { t } = useTranslation();
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    email: '',
    phone: '',
  });
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [loading, setLoading] = useState(false);
  const [serverError, setServerError] = useState('');

  const validate = () => {
    const newErrors: Record<string, string> = {};
    if (!formData.username.trim()) newErrors.username = t('register.validation.usernameRequired');
    if (!formData.email.trim()) {
      newErrors.email = t('register.validation.emailRequired');
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = t('register.validation.emailInvalid');
    }
    if (!formData.phone.trim()) {
      newErrors.phone = t('register.validation.phoneRequired');
    } else if (!/^\d{10,15}$/.test(formData.phone)) {
      newErrors.phone = t('register.validation.phoneInvalid');
    }
    if (!formData.password) {
      newErrors.password = t('register.validation.passwordRequired');
    } else if (formData.password.length < 6) {
      newErrors.password = t('register.validation.passwordMin');
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    if (errors[name]) {
      setErrors({ ...errors, [name]: '' });
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validate()) return;
    
    setLoading(true);
    setServerError('');

    try {
      const merchantId = await registerApi(formData);
      onRegisterSuccess(merchantId);
    } catch (err: any) {
      setServerError(err.message || t('register.error'));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-50 px-4 py-12 sm:px-6 lg:px-8">
      <div className="w-full max-w-md space-y-8 rounded-xl bg-white p-8 shadow-lg">
        <div>
          <h2 className="mt-6 text-center text-3xl font-bold tracking-tight text-gray-900">
            {t('register.title')}
          </h2>
          <p className="mt-2 text-center text-sm text-gray-600">
            {t('register.subtitle')}
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
                {t('register.username')}
              </label>
              <input
                id="username"
                name="username"
                type="text"
                className={`relative block w-full rounded-md border-0 py-1.5 px-3 text-gray-900 ring-1 ring-inset ${errors.username ? 'ring-red-500' : 'ring-gray-300'} placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-primary sm:text-sm sm:leading-6`}
                placeholder={t('register.usernamePlaceholder')}
                value={formData.username}
                onChange={handleChange}
              />
              {errors.username && <p className="mt-1 text-xs text-red-500">{errors.username}</p>}
            </div>
            <div>
              <label htmlFor="email" className="block text-sm font-medium text-gray-700">
                {t('register.email')}
              </label>
              <input
                id="email"
                name="email"
                type="email"
                className={`relative block w-full rounded-md border-0 py-1.5 px-3 text-gray-900 ring-1 ring-inset ${errors.email ? 'ring-red-500' : 'ring-gray-300'} placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-primary sm:text-sm sm:leading-6`}
                placeholder={t('register.emailPlaceholder')}
                value={formData.email}
                onChange={handleChange}
              />
              {errors.email && <p className="mt-1 text-xs text-red-500">{errors.email}</p>}
            </div>
            <div>
              <label htmlFor="phone" className="block text-sm font-medium text-gray-700">
                {t('register.phone')}
              </label>
              <input
                id="phone"
                name="phone"
                type="text"
                className={`relative block w-full rounded-md border-0 py-1.5 px-3 text-gray-900 ring-1 ring-inset ${errors.phone ? 'ring-red-500' : 'ring-gray-300'} placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-primary sm:text-sm sm:leading-6`}
                placeholder={t('register.phonePlaceholder')}
                value={formData.phone}
                onChange={handleChange}
              />
              {errors.phone && <p className="mt-1 text-xs text-red-500">{errors.phone}</p>}
            </div>
            <div>
              <label htmlFor="password" title={t('register.password')} className="block text-sm font-medium text-gray-700">
                {t('register.password')}
              </label>
              <input
                id="password"
                name="password"
                type="password"
                className={`relative block w-full rounded-md border-0 py-1.5 px-3 text-gray-900 ring-1 ring-inset ${errors.password ? 'ring-red-500' : 'ring-gray-300'} placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-primary sm:text-sm sm:leading-6`}
                placeholder={t('register.passwordPlaceholder')}
                value={formData.password}
                onChange={handleChange}
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
              {loading ? t('register.submitting') : t('register.submit')}
            </Button>
          </div>

          <div className="text-center">
            <button
              type="button"
              onClick={onGoToLogin}
              className="text-sm font-medium text-primary hover:text-primary/80"
            >
              {t('register.hasAccount')}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default RegisterPage;
