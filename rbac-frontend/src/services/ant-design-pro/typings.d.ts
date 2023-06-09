// @ts-ignore
/* eslint-disable */

declare namespace API {

  type LoginResponse = {
    userVO?: CurrentUser;
    token?: string;
  };

  type CurrentUser = {
    id?: string;
    username?: string;
    avatar?: string;
    nickName?: string;
    roleIds?: number[];
    permissionIds?: number[];
  };

  type PageParams = {
    current?: number;
    pageSize?: number;
  };

  type UserListItem = {
    id?: number;
    username?: string;
    userStatus?: number;
    roleIds?: number[];
    orgIds?: number[];
  };

  type UserList = {
    records?: UserListItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  };


  type RuleListItem = {
    key?: number;
    disabled?: boolean;
    href?: string;
    avatar?: string;
    name?: string;
    owner?: string;
    desc?: string;
    callNo?: number;
    status?: number;
    updatedAt?: string;
    createdAt?: string;
    progress?: number;
  };

  type RuleList = {
    data?: RuleListItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  };

  type CaptchaInfo = {
    code?: number;
    msg?: string;
    data?: string;
  };

  type LoginParams = {
    account?: string;
    password?: string;
    autoLogin?: boolean;
    type?: string;
  };

  type ErrorResponse = {
    /** 业务约定的错误码 */
    errorCode: string;
    /** 业务上的错误信息 */
    errorMessage?: string;
    /** 业务上的请求是否成功 */
    success?: boolean;
  };

  type NoticeIconList = {
    data?: NoticeIconItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  };

  type NoticeIconItemType = 'notification' | 'message' | 'event';

  type NoticeIconItem = {
    id?: string;
    extra?: string;
    key?: string;
    read?: boolean;
    avatar?: string;
    title?: string;
    status?: string;
    datetime?: string;
    description?: string;
    type?: NoticeIconItemType;
  };
}
