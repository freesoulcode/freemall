import React from 'react';
import { useTranslation } from 'react-i18next';
import { Button } from './ui/button';

const LanguageSwitcher: React.FC = () => {
  const { i18n } = useTranslation();

  return (
    <div className="flex gap-2">
      <Button
        variant={i18n.language.startsWith('zh') ? 'default' : 'outline'}
        size="sm"
        onClick={() => i18n.changeLanguage('zh')}
      >
        中文
      </Button>
      <Button
        variant={i18n.language.startsWith('en') ? 'default' : 'outline'}
        size="sm"
        onClick={() => i18n.changeLanguage('en')}
      >
        English
      </Button>
    </div>
  );
};

export default LanguageSwitcher;
