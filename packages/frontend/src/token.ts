import Cookies from 'js-cookie'

const TokenKey1 = 'Token'

export function getToken() {
  return Cookies.get(TokenKey1)
}

export function setToken(token: string) {
  return Cookies.set(TokenKey1, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey1)
}
