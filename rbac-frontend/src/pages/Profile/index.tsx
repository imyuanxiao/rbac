import { fetchRoleOptions } from "@/pages/UserList";
import { FormattedMessage } from "@@/exports";
import { AntDesignOutlined } from '@ant-design/icons';
import {PageContainer, ProField, ProForm, ProFormSelect, ProFormText} from '@ant-design/pro-components';
import { useModel } from '@umijs/max';
import {Avatar, Card, Descriptions, Tabs, theme} from 'antd';
import React from 'react';


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

  const handleUpdateInfo = async (values: any) => {
    // 发送更新基础资料的请求
    try {
      await API.updateUserInfo(values);
      // 更新成功的处理
    } catch (error) {
      // 更新失败的处理
    }
  };

  const handleUpdatePassword = async (values: any) => {
    // 发送更新密码的请求
    try {
      await API.updatePassword(values);
      // 更新成功的处理
    } catch (error) {
      // 更新失败的处理
    }
  };

  const BasicInfoTab: React.FC<{
    userInfo: API.CurrentUser;
  }> = ({userInfo}) => {
    return (
      <ProForm
        initialValues={{

        }}
      >
        <ProFormText

        />


      </ProForm>
    );
  };

  const PasswordTab: React.FC = () => {
    return (
      <div>
        {/* 在这里放置修改密码的表单或组件 */}
        <h2>修改密码</h2>
        <p>这是修改密码的内容</p>
      </div>
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
            children: <BasicInfoTab />,
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
