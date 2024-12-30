import type { UseFetchOptions } from 'nuxt/app';

interface RequestConfig extends UseFetchOptions<any> {
  timeout?: number;
}

export interface HttpResponse<T> {
  code: string;
  message: string;
  data?: T;
  success: boolean;
}

interface FetchContext {
  options: any;
  response: any;
}

export class ApiError extends Error {}

export class HttpError extends Error {}

class Http {
  private baseURL = 'http://localhost:8080/api'; // 替换为您的实际API地址
  private timeout = 5000;
  private successCode = '000000';
  private errorCode = '111111';

  async doGet<T>(
    url: string,
    params: Record<string, any> = {}
  ): Promise<HttpResponse<T>> {
    const query: { [key: string]: any } = {};
    for (const c in params) {
      if (
        Object.hasOwnProperty.call(params, c) &&
        params[c] !== undefined &&
        params[c] !== null
      ) {
        query[c] = params[c]!;
      }
    }
    return this.request<T>(url, {
      method: 'GET',
      query,
    });
  }

  doPost<T>(url: string, data: object): Promise<HttpResponse<T>> {
    return this.request<T>(url, {
      method: 'POST',
      body: data,
    });
  }

  private request<T>(
    url: string,
    config: RequestConfig = {}
  ): Promise<HttpResponse<T>> {
    return new Promise<HttpResponse<T>>((resolve, reject) => {
      const finalConfig: object = {
        baseURL: this.baseURL,
        timeout: this.timeout,
        ...config,
        onRequest: this.onRequest,
        onResponse: (o: FetchContext) => {
          const res = this.onResponse<T>(o);
          resolve(res);
        },
        onResponseError: (o: FetchContext) => {
          this.onResponseError(o);
          reject();
        },
      };

      return $fetch<T>(url, finalConfig);
    });
  }

  private onRequest({ options }: FetchContext) {
    const userStore = useUserStore();
    if (userStore.isLoggedIn) {
      options.headers.set(
        'Authorization',
        `Bearer ${localStorage.getItem('token')}`
      );
    }
  }

  private onResponse<T>({ response }: FetchContext): HttpResponse<T> {
    return response._data;
  }

  onResponseError({ response }: FetchContext) {
    const status = response.status;
    switch (status) {
      case 401: {
        // const userStore = useUserStore();
        // userStore.logout();
        // navigateTo('/login');
        throw new HttpError('没有登录');
      }
      case 403:
        throw new HttpError('没有权限访问');
      case 404:
        throw new HttpError('请求的资源不存在');
      case 500:
        throw new HttpError('服务器错误');
      default:
        throw new HttpError('网络错误');
    }
  }
}

export const $http = new Http();
