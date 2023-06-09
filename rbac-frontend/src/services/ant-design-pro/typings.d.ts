// @ts-ignore
/* eslint-disable */

declare namespace API {

  type LoginResponse = {
    userVO?: CurrentUser;
    token?: string;
  };

  type UserProfileParam = {
    userPhone? : string;
    userEmail? :string;
    nickName? : string;
  };

  type UserPasswordParam = {
    oldPassword? : string;
    newPassword? :string;
  };

  type CurrentUser = {
    id?: string;
    username?: string;
    userPhone?: string;
    userEmail?: string;
    avatar?: string;
    nickName?: string;
    roleIds?: number[];
    permissionIds?: number[];
  };

  type ListData<T> = {
    records?: T[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  };

  type UserListItem = {
    id?: number;
    username?: string;
    userStatus?: number;
    userPhone? : string;
    userEmail? :string;
    roleIds?: number[];
    orgIds?: number[];
  };

  type DataListItem = {
    id?: number;
    dataName?: string;
    orgId?: number;
  };

  type RoleListItem = {
    id?: number;
    roleName?: string;
    description?: string;
    permissionIds?: number[];
  };

  type LoginHistoryListItem = {
    id?: number;
    username?: string;
    type?: number;
    createdTime?: Date;
    ipAddress?: string;
    userAgent?: string;
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

  // type ErrorResponse = {
  //   /** 业务约定的错误码 */
  //   errorCode: string;
  //   /** 业务上的错误信息 */
  //   errorMessage?: string;
  //   /** 业务上的请求是否成功 */
  //   success?: boolean;
  // };

  // type NoticeIconList = {
  //   data?: NoticeIconItem[];
  //   /** 列表的内容总数 */
  //   total?: number;
  //   success?: boolean;
  // };
  //
  // type NoticeIconItemType = 'notification' | 'message' | 'event';
  //
  // type NoticeIconItem = {
  //   id?: string;
  //   extra?: string;
  //   key?: string;
  //   read?: boolean;
  //   avatar?: string;
  //   title?: string;
  //   status?: string;
  //   datetime?: string;
  //   description?: string;
  //   type?: NoticeIconItemType;
  // };
}
