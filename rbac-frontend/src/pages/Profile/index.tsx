import { fetchRoleOptions } from "@/pages/UserList";
import {FormattedMessage, useIntl} from "@@/exports";
import { AntDesignOutlined } from '@ant-design/icons';
import {PageContainer, ProField, ProForm, ProFormText} from '@ant-design/pro-components';
import { useModel } from '@umijs/max';
import {Avatar, Card, Descriptions, message, Tabs, theme} from 'antd';
import React from 'react';
import {updatePassword, updateUserProfile} from "@/services/ant-design-pro/api";
import {flushSync} from "react-dom";


/**
 * 每个单独的卡片，为了复用样式抽成了组件
 * @param param0
 * @returns
 */
const InfoCard: React.FC<{
  component: React.ReactNode
}> = ({ component  }) => {

  const { useToken } = theme;

  const { token } = useToken();


  return (
    <div
      style={{
        backgroundColor: token.colorBgContainer,
        boxShadow: token.boxShadow,
        borderRadius: '8px',
        fontSize: '14px',
        color: token.colorTextSecondary,
        lineHeight: '22px',
        padding: '10px 19px',
        minWidth: '220px',
        flex: 1,
      }}
    >
      {component}
    </div>
  );
};

const MyInfoCard: React.FC<{
  userInfo: API.CurrentUser;
}> = ({userInfo}) => {

  const ProfileTab: React.FC<{
    userInfo: API.CurrentUser;
  }> = ({userInfo}) => {
    return (
      <>
         <div
            style={{
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
              marginBottom: '1.5em',
              width: '100%',
            }}
          >
            <Avatar
              size={{ xs: 50, sm: 50, md: 100, lg: 100, xl: 100, xxl: 100 }}
              icon={<AntDesignOutlined />}
              src={`${userInfo.avatar}`}
            />
          </div>
          <Descriptions bordered column={{ xxl: 4, xl: 3, lg: 3, md: 3, sm: 2, xs: 1 }}>
            <Descriptions.Item
              label={<FormattedMessage id="typings.CurrentUser.username" defaultMessage="Username" />}
              span={3}
            >
              {userInfo.username}
            </Descriptions.Item>
            <Descriptions.Item
              label={<FormattedMessage id="typings.CurrentUser.nickName" defaultMessage="Nick name" />}
              span={3}
            >
              {userInfo.nickName}
            </Descriptions.Item>
            <Descriptions.Item
              label={<FormattedMessage id="typings.CurrentUser.roleIds" defaultMessage="Role" />}
              span={3}
            >
              <ProField
                mode="read"
                valueType="select"
                request={fetchRoleOptions}
                fieldProps={{
                  mode: 'multiple',
                  value: userInfo.roleIds,
                }}
              />
            </Descriptions.Item>
          </Descriptions>
      </>
    );
  };

  return (
    <Tabs
      defaultActiveKey="userInfo"
      style={{
        width: '100%'
      }}
      items={[
        {
          key: 'userInfo',
          label: `用户资料`,
          children: <ProfileTab userInfo={userInfo} />,
        }
      ]}
    />
  );
}

const UpdateInfoCard: React.FC<{
  userInfo: API.CurrentUser;
}> = ({ userInfo }) => {

  const { initialState, setInitialState } = useModel('@@initialState');

  const {currentUser} = initialState;

  const handleUpdateProfile = async (fields: API.UserProfileParam) => {
    const hide = message.loading('Updating');
    try {
      await updateUserProfile(fields);
      hide();
      message.success('Update is successful');
      return true;
    } catch (error) {
      hide();
      message.error('Update failed, please try again!');
      return false;
    }
  };

  const fetchUserInfo = async () => {
    const userInfo = await initialState?.fetchUserInfo?.();
    if (userInfo) {
      flushSync(() => {
        setInitialState((s) => ({
          ...s,
          currentUser: userInfo,
        }));
      });
    }
  };

  const handleUpdatePassword = async (values: API.UserPasswordParam) => {
    const hide = message.loading('Updating');
    try {
      await updatePassword(values);
      hide();
      message.success('Update is successful');
      return true;
    } catch (error) {
      hide();
      message.error('Update failed, please try again!');
      return false;
    }
  };

  const BasicInfoTab: React.FC<{
    userInfo: API.CurrentUser;
  }> = ({userInfo}) => {
    const intl = useIntl();

    return (
      <ProForm
        onFinish={
          async (value) => {
            const success = await handleUpdateProfile(value);
            if(success){
                //重新获取currentUser
              await fetchUserInfo();
            }
          }
        }
        initialValues={{
          nickName: userInfo.nickName,
          userPhone: userInfo.userPhone,
          userEmail: userInfo.userEmail
        }}
      >
        <ProFormText
          name="nickName"
          label={intl.formatMessage({
            id: 'typings.CurrentUser.nickName',
            defaultMessage: 'Nick name',
          })}
          rules={[
            {
              required: true,
              message: (
                <FormattedMessage
                  id="rules.message.profile.nickName"
                  defaultMessage='Please enter a nick name'
                />
              ),
            },
          ]}
        />
        <ProFormText
          name="userPhone"
          label={intl.formatMessage({
            id: 'typings.UserListItem.userPhone',
            defaultMessage: 'Phone',
          })}
        />
        <ProFormText
          name="userEmail"
          label={intl.formatMessage({
            id: 'typings.UserListItem.userEmail',
            defaultMessage: 'Email',
          })}
          rules={[
            {
              type: 'email',
              message:(
                <FormattedMessage
                  id="rules.message.profile.userEmail"
                  defaultMessage='Please enter a valid email'
                />
              ),
            },
          ]}
        />
      </ProForm>
    );
  };

  const PasswordTab: React.FC = () => {

    const intl = useIntl();

    const validatePsw = ({ getFieldValue }) => {
      return {
        validator: (_, value) => {
          if (!value || getFieldValue("newPassword") === value) {
            return Promise.resolve();
          }
          return Promise.reject(new Error("两次输入密码不一致，请重新输入！"));
        },
      };
    };

    return (
      <ProForm
        onFinish={
          async (value) => {
            const success = await handleUpdatePassword(value);
            if(success){
              //重新获取currentUser
              await fetchUserInfo();
            }
          }
        }
      >
        <ProFormText
          name="oldPassword"
          label={intl.formatMessage({
            id: 'typings.UserPasswordParam.oldPassword',
            defaultMessage: 'Old Password',
          })}
          rules={[
            {
              required: true,
              message: (
                <FormattedMessage
                  id="rules.message.password.oldPassword"
                  defaultMessage='Please enter your old password'
                />
              ),
            }]
          }
        />
        <ProFormText
          name="newPassword"
          label={intl.formatMessage({
            id: 'typings.UserPasswordParam.newPassword',
            defaultMessage: 'New Password',
          })}
          rules={[
            {
              required: true,
              message: (
                <FormattedMessage
                  id="rules.message.password.newPassword"
                  defaultMessage='Please enter new password'
                />
              ),
            }]
            }
        />
        <ProFormText
          name="checkNewPassword"
          label={intl.formatMessage({
            id: 'rules.message.password.newPassword',
            defaultMessage: 'Please enter new password',
          })}
          dependencies={["newPassword"]}
          validateTrigger="onBlur"
          rules={[
            validatePsw,
            {
              required: true,
              message: (
                <FormattedMessage
                  id="rules.message.password.checkNewPassword"
                  defaultMessage='Please check new password'
                />
              ),
            }
          ]}
        />
      </ProForm>
    );
  };

  return (
  <Tabs defaultActiveKey="basic"
        style={{
          width: '100%',
        }}
        items={[
          {
            key: 'basic',
            label: `基础资料`,
            children: <BasicInfoTab userInfo={userInfo}/>,
          },
          {
            key: 'password',
            label: `修改密码`,
            children: <PasswordTab />,
          }
        ]}
  />
  );
};


const Profile: React.FC = () => {
  const { token } = theme.useToken();
  const { initialState } = useModel('@@initialState');
  const { currentUser } = initialState;

  return (
    <PageContainer>
      <Card
        style={{
          borderRadius: 8,
        }}
        bodyStyle={{
          backgroundImage:
            initialState?.settings?.navTheme === 'realDark'
              ? 'background-image: linear-gradient(75deg, #1A1B1F 0%, #191C1F 100%)'
              : 'background-image: linear-gradient(75deg, #FBFDFF 0%, #F5F7FF 100%)',
        }}
      >
        <div
          style={{
            backgroundPosition: '100% -30%',
            backgroundRepeat: 'no-repeat',
            backgroundSize: '274px auto',
            backgroundImage:
              "url('https://gw.alipayobjects.com/mdn/rms_a9745b/afts/img/A*BuFmQqsB2iAAAAAAAAAAAAAAARQnAQ')",
          }}
        >
          <div
            style={{
              display: 'flex',
              flexWrap: 'wrap',
              gap: 16,
            }}
          >
            <InfoCard
              component={<MyInfoCard userInfo={currentUser} />}
            />
            <InfoCard
              component={<UpdateInfoCard userInfo={currentUser} />}
            />
          </div>
        </div>
      </Card>
    </PageContainer>
  );
};

export default Profile;
