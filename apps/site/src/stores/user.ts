export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    isLoggedIn: false,
    userInfo: null,
  }),

  getters: {},

  actions: {
    setLoginState(status: boolean) {
      this.isLoggedIn = status;
    },

    setUserInfo(info: any) {
      this.userInfo = info;
    },

    logout() {
      this.isLoggedIn = false;
      this.userInfo = null;
    },
  },

  persist: true,
});

export interface LoginResponse {
  token: string;
  userInfo: UserInfo;
}

export interface UserInfo {
  id: string;
  email: string;
  username: string;
  nickname: string;
  description: string;
  fansCount: number;
  followCount: number;
}

interface UserState {
  isLoggedIn: boolean;
  userInfo?: UserInfo | null;
}
