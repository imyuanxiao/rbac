import jwt_decode from "jwt-decode";
import {updateToken} from "@/services/ant-design-pro/api";

const loginPath = '/user/login';

export function getTokenExpireTime(token: string): number | null {
  const decoded: any = jwt_decode(token);
  if (decoded && typeof decoded.exp === 'number') {
    return decoded.exp;
  }
  return null;
}

export function checkTokenExpiration(): void {
  if (window.location.pathname === loginPath) {
    console.log("登录页面不检查")
    return;
  }
  console.log("检查token")
  const token = localStorage.getItem('token');
  if (!token) {
    return;
  }
  const expireTime = getTokenExpireTime(token);
  if (!expireTime) {
    return;
  }
  const now = Math.floor(Date.now() / 1000);
  // 过期前两分半进行检查和更新
  if (now >= expireTime - 150) {
    console.log("token会过期")
    // Token will expire within 60 seconds, update it
    updateToken()
      .catch((error) => {
        console.error('Failed to update token:', error);
      });
  }
}

setInterval(checkTokenExpiration, 120 * 1000);
