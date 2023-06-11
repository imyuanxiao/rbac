import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import React from 'react';

const Footer: React.FC = () => {
  const intl = useIntl();
  const defaultMessage = intl.formatMessage({
    id: 'app.copyright.produced',
    defaultMessage: 'imyuanxiao',
  });

  const currentYear = new Date().getFullYear();

  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'RoleMaster',
          title: 'RoleMaster',
          href: 'https://github.com/imyuanxiao/rbac',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/imyuanxiao',
          blankTarget: true,
        },
        {
          key: 'imyuanxiao',
          title: 'imyuanxiao',
          href: 'https://imyuanxiao.com/',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
