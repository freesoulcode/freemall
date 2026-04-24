import React, { useState } from 'react';
import { verifyApi } from '../api/merchant';
import { Button } from '@/components/ui/button';
import { useTranslation } from 'react-i18next';

interface VerifyPageProps {
  merchantId: number;
  onVerifySuccess: () => void;
}

const VerifyPage: React.FC<VerifyPageProps> = ({ merchantId, onVerifySuccess }) => {
  const { t } = useTranslation();
  const [code, setCode] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [serverError, setServerError] = useState('');

  const validate = () => {
    if (!code.trim()) {
      setError(t('verify.validation.codeRequired'));
      return false;
    }
    if (code.length !== 6) {
      setError(t('verify.validation.codeLength'));
      return false;
    }
    setError('');
    return true;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validate()) return;

    setLoading(true);
    setServerError('');

    try {
      await verifyApi({ merchantId, code });
      onVerifySuccess();
    } catch (err: any) {
      setServerError(err.message || t('verify.error'));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-50 px-4 py-12 sm:px-6 lg:px-8">
      <div className="w-full max-w-md space-y-8 rounded-xl bg-white p-8 shadow-lg">
        <div>
          <h2 className="mt-6 text-center text-3xl font-bold tracking-tight text-gray-900">
            {t('verify.title')}
          </h2>
          <p className="mt-2 text-center text-sm text-gray-600">
            {t('verify.subtitle')}
          </p>
          <p className="mt-1 text-center text-xs text-gray-400">
            {t('verify.demoHint')}
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
              <label htmlFor="code" className="block text-sm font-medium text-gray-700">
                {t('verify.code')}
              </label>
              <input
                id="code"
                name="code"
                type="text"
                maxLength={6}
                className={`relative block w-full rounded-md border-0 py-1.5 px-3 text-gray-900 ring-1 ring-inset ${error ? 'ring-red-500' : 'ring-gray-300'} placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-primary sm:text-sm sm:leading-6 text-center tracking-[1em] font-bold`}
                placeholder={t('verify.codePlaceholder')}
                value={code}
                onChange={(e) => {
                  setCode(e.target.value);
                  if (error) setError('');
                }}
              />
              {error && <p className="mt-1 text-xs text-red-500">{error}</p>}
            </div>
          </div>

          <div>
            <Button
              type="submit"
              className="w-full"
              disabled={loading}
            >
              {loading ? t('verify.submitting') : t('verify.submit')}
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default VerifyPage;
