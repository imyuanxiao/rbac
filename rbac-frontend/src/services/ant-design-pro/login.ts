// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 发送验证码 POST /api/auth/captcha */
export async function getCaptcha(
  mobile?: string,
  options?: { [key: string]: any },
) {
  return request<API.CaptchaInfo>('/api/auth/captcha', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: {
      phone: mobile
    },
    ...(options || {}),
  });
}
