import {
  ProForm,
  ProFormSelect,
  ProFormText,
} from '@ant-design/pro-components';
import { FormattedMessage, useIntl } from '@umijs/max';
import {Modal} from 'antd';
import React from 'react';
import {orgEnum, userStatusOptions} from "@/utils/comonValue";
import {fetchRoleOptions} from "@/pages/UserList";

export type FormValueType = {
  target?: string;
  template?: string;
  type?: string;
  time?: string;
  frequency?: string;
} & Partial<API.UserListItem>;

export type UpdateFormProps = {
  onCancel: (flag?: boolean, formVals?: FormValueType) => void;
  onSubmit: (values: FormValueType) => Promise<void>;
  updateModalOpen: boolean;
  values: Partial<API.UserListItem>;
};

const UpdateForm: React.FC<UpdateFormProps> = (props) => {
  const intl = useIntl();
  return (
    <Modal
      width={640}
      bodyStyle={{ padding: '32px 40px 48px' }}
      destroyOnClose
      title={intl.formatMessage({
        id: 'pages.searchTable.updateForm.edit',
        defaultMessage: '编辑',
      })}
      open={props.updateModalOpen}
      onCancel={() => {
        props.onCancel();
      }}
      footer={null}
    >
      <ProForm
        onFinish={props.onSubmit}
        initialValues={{
          id: props.values.id,
          username: props.values.username,
          userStatus: props.values.userStatus,
          userPhone: props.values.userPhone,
          userEmail: props.values.userEmail,
          roleIds: props.values.roleIds?.map(id => id.toString()),
          orgIds: props.values.orgIds?.map(id => id.toString()),
        }}
        style={{
          display: 'grid',
          gridTemplateColumns: '1fr 1fr',
          gap: '16px',
        }}
      >
          <ProFormText
            name="id"
            label={intl.formatMessage({
              id: 'typings.UserListItem.id',
              defaultMessage: 'User ID',
            })}
            hidden
            disabled
          />
          <ProFormText
            name="username"
            label={intl.formatMessage({
              id: 'typings.UserListItem.username',
              defaultMessage: 'Username',
            })}
            width="sm"
            rules={[
              {
                required: true,
                message: (
                  <FormattedMessage
                    id="pages.searchTable.updateForm.userName.nameRules"
                    defaultMessage="请输入用户名！"
                  />
                ),
              },
            ]}
          />
          <ProFormSelect
            name="userStatus"
            width="sm"
            label={intl.formatMessage({
              id: 'typings.UserListItem.userStatus',
              defaultMessage: 'Status',
            })}
            options={userStatusOptions}
          />
          <ProFormSelect
            name="roleIds"
            mode="multiple"
            width="sm"
            label={intl.formatMessage({
              id: 'typings.UserListItem.roleIds',
              defaultMessage: 'Role',
            })}
            request={fetchRoleOptions}
          />
        <ProFormText
          name="userPhone"
          label={intl.formatMessage({
            id: 'typings.UserListItem.userPhone',
            defaultMessage: 'Phone',
          })}
          width="sm"
        />
        <ProFormText
          name="userEmail"
          label={intl.formatMessage({
            id: 'typings.UserListItem.userEmail',
            defaultMessage: 'Email',
          })}
          width="sm"
        />
        <ProFormSelect
          name="orgIds"
          mode="multiple"
          width="sm"
          label={intl.formatMessage({
            id: 'typings.UserListItem.orgIds',
            defaultMessage: 'Organization',
          })}
          valueEnum={orgEnum}
        />
      </ProForm>

    </Modal>
  );
};

export default UpdateForm;
