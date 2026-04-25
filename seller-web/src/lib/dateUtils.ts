import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import timezone from 'dayjs/plugin/timezone';
import relativeTime from 'dayjs/plugin/relativeTime';
import 'dayjs/locale/zh-cn';
import 'dayjs/locale/en';

// 初始化插件
dayjs.extend(utc);
dayjs.extend(timezone);
dayjs.extend(relativeTime);

/**
 * 格式化日期时间为本地时区显示
 * @param dateStr ISO 格式的 UTC 时间字符串，如 "2026-04-25T13:03:45Z"
 * @param locale 语言代码，如 'zh' 或 'en'
 * @returns 格式化后的日期时间字符串
 */
export function formatDateTime(dateStr: string | null | undefined, locale: string = 'zh'): string {
  if (!dateStr) return '';
  
  const date = dayjs.utc(dateStr);
  if (!date.isValid()) return dateStr;
  
  // 转换为本地时区
  const localDate = date.local();
  
  // 设置语言
  const localeCode = locale === 'zh' ? 'zh-cn' : 'en';
  dayjs.locale(localeCode);
  
  return localDate.format('YYYY-MM-DD HH:mm');
}

/**
 * 格式化为相对时间（如 "3分钟前"）
 */
export function formatRelativeTime(dateStr: string | null | undefined, locale: string = 'zh'): string {
  if (!dateStr) return '';
  
  const date = dayjs.utc(dateStr);
  if (!date.isValid()) return dateStr;
  
  // 设置语言
  const localeCode = locale === 'zh' ? 'zh-cn' : 'en';
  dayjs.locale(localeCode);
  
  return date.fromNow();
}

/**
 * 格式化为完整日期时间（带时区）
 */
export function formatFullDateTime(dateStr: string | null | undefined, locale: string = 'zh'): string {
  if (!dateStr) return '';
  
  const date = dayjs.utc(dateStr);
  if (!date.isValid()) return dateStr;
  
  const localDate = date.local();
  
  const localeCode = locale === 'zh' ? 'zh-cn' : 'en';
  dayjs.locale(localeCode);
  
  return localDate.format('YYYY-MM-DD HH:mm:ss Z');
}
