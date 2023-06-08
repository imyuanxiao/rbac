import { Footer, Question, SelectLang, AvatarDropdown, AvatarName } from '@/components';
import { LinkOutlined } from '@ant-design/icons';
import type { Settings as LayoutSettings } from '@ant-design/pro-components';
import { SettingDrawer } from '@ant-design/pro-components';
import type { RunTimeLayoutConfig } from '@umijs/max';
import {history, Link, RequestConfig} from '@umijs/max';
import defaultSettings from '../config/defaultSettings';
import { errorConfig } from './requestErrorConfig';
import { currentUser as queryCurrentUser } from '@/services/ant-design-pro/api';
import React from 'react';
const isDev = process.env.NODE_ENV === 'development';
const loginPath = '/user/login';

/**
 * @see  https://umijs.org/zh-CN/plugins/plugin-initial-state
 * */
export async function getInitialState(): Promise<{
  settings?: Partial<LayoutSettings>;
  currentUser?: API.CurrentUser;
  loading?: boolean;
  fetchUserInfo?: () => Promise<API.CurrentUser | undefined>;
}> {
  const fetchUserInfo = async () => {
    try {
      const msg = await queryCurrentUser({
        skipErrorHandler: true,
      });
      return msg.data;
    } catch (error) {
      history.push(loginPath);
    }
    return undefined;
  };
  // 如果不是登录页面，执行
  const { location } = history;
  if (location.pathname !== loginPath) {
    const currentUser = await fetchUserInfo();
    return {
      fetchUserInfo,
      currentUser,
      settings: defaultSettings as Partial<LayoutSettings>,
    };
  }
  return {
    fetchUserInfo,
    settings: defaultSettings as Partial<LayoutSettings>,
  };
}

// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({ initialState, setInitialState }) => {
  return {
    actionsRender: () => [<Question key="doc" />, <SelectLang key="SelectLang" />],
    avatarProps: {
      src: initialState?.currentUser?.avatar,
      title: <AvatarName />,
      render: (_, avatarChildren) => {
        return <AvatarDropdown>{avatarChildren}</AvatarDropdown>;
      },
    },
    waterMarkProps: {
      content: initialState?.currentUser?.name,
    },
    footerRender: () => <Footer />,
    onPageChange: () => {
      const { location } = history;
      // 如果没有登录，重定向到 login
      if (!initialState?.currentUser && location.pathname !== loginPath) {
        history.push(loginPath);
      }
    },
    layoutBgImgList: [
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/D2LWSqNny4sAAAAAAAAAAAAAFl94AQBr',
        left: 85,
        bottom: 100,
        height: '303px',
      },
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/C2TWRpJpiC0AAAAAAAAAAAAAFl94AQBr',
        bottom: -68,
        right: -45,
        height: '303px',
      },
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/F6vSTbj8KpYAAAAAAAAAAAAAFl94AQBr',
        bottom: 0,
        left: 0,
        width: '331px',
      },
    ],
    links: isDev
      ? [
          <Link key="openapi" to="/umi/plugin/openapi" target="_blank">
            <LinkOutlined />
            <span>OpenAPI 文档</span>
          </Link>,
        ]
      : [],
    menuHeaderRender: undefined,
    // 自定义 403 页面
    // unAccessible: <div>unAccessible</div>,
    // 增加一个 loading 的状态
    childrenRender: (children) => {
      // if (initialState?.loading) return <PageLoading />;
      return (
        <>
          {children}
          {isDev && (
            <SettingDrawer
              disableUrlParams
              enableDarkTheme
              settings={initialState?.settings}
              onSettingChange={(settings) => {
                setInitialState((preInitialState) => ({
                  ...preInitialState,
                  settings,
                }));
              }}
            />
          )}
        </>
      );
    },
    ...initialState?.settings,
  };
};

// TODO 此处配置token
/**
 * 请求拦截器
 * @param url
 * @param options
 */
const authHeaderInterceptor = (url: string, options: RequestConfig) => {
  const token: string | null = localStorage.getItem("token")
  const authHeader = { Authorization: token };
  if (token !== null) {
    return {
      url: `${url}`,
      options: { ...options, interceptors: true, headers: authHeader },
    };
  }
  return { url, options };
};

/**
 * 响应拦截器
 * @param response
 * @param options
 */
const demoResponseInterceptors = (response: Response, options: RequestConfig) => {

  console.log(response)


  // if(response.data.code !== 0){
  //   console.log("响应出错" + response.data)
  // }else{
  //   console.log("响应正常")
  // }

  return response;
};

/**
 * @name request 配置，可以配置错误处理
 * 它基于 axios 和 ahooks 的 useRequest 提供了一套统一的网络请求和错误处理方案。
 * @doc https://umijs.org/docs/max/request#配置
 */
export const request: RequestConfig = {
  // 新增自动添加AccessToken的请求前拦截器
  // 后端接口返回的数据规范不满足umiJS的默认配置,可以通过配置errorConfig.adaptor进行适配.
  // 全局统一错误处理
  errorConfig: { // 错误处理
    adaptor: (resData) => { // 适配器
      return {
        ...resData, // 适配器里面要返回一个对象，里面包含了 success 和 errorMessage
        success: resData.code === 0, // 这里的 success 表示是否请求成功
        errorMessage: resData.data, // 这里的 errorMessage 表示错误信息
      };
    },
  },
  // 全局接口异常处理
  errorHandler: (error) => { // errorHandler: 异常处理程序
    // console.log('error:',error)
    const { response } = error;
    if (response && response.status) {
      // const errorText = codeMessage[response.status] || response.statusText;
      const { status, url } = response;
      message.error(`请求错误 ${status}: ${url}`)
      /*notification.error({ //右侧提示框 notification需要 import { notification } from 'antd';
        message: `请求错误 ${status}: ${url}`,
        description: errorText,
      });*/
    }
    if (!response) {
      message.error('您的网络发生异常，无法连接服务器')
      /*notification.error({ // 右侧提示框
        description: '您的网络发生异常，无法连接服务器',
        message: '网络异常',
      });*/
    }
    throw error;
  },
  responseInterceptors: [demoResponseInterceptors]
};

