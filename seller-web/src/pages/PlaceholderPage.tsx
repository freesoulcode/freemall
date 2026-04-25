import { useTranslation } from "react-i18next";
import type { LucideIcon } from "lucide-react";

interface PlaceholderPageProps {
  titleKey: string;
  descriptionKey: string;
  icon: LucideIcon;
}

const PlaceholderPage: React.FC<PlaceholderPageProps> = ({ titleKey, descriptionKey, icon: Icon }) => {
  const { t } = useTranslation();

  return (
    <div className="flex flex-1 items-center justify-center">
      <div className="flex flex-col items-center gap-4 text-center">
        <div className="flex h-16 w-16 items-center justify-center rounded-full bg-muted">
          <Icon className="h-8 w-8 text-muted-foreground" />
        </div>
        <h2 className="text-2xl font-bold tracking-tight">{t(titleKey)}</h2>
        <p className="text-muted-foreground max-w-md">{t(descriptionKey)}</p>
      </div>
    </div>
  );
};

export default PlaceholderPage;