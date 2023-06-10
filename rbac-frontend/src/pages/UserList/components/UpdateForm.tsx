import {
  ProForm,
  ProFormDateTimePicker,
  ProFormRadio,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
  StepsForm,
} from '@ant-design/pro-components';
import { FormattedMessage, useIntl } from '@umijs/max';
import {Modal, Tag} from 'antd';
import React from 'react';
import {orgEnum, roleEnum, userStatusOptions} from "@/utils/comonValue";

export type FormValueType = {
  target?: string;
  template?: string;
  type?: string;
  time?: string;
  frequency?: string;
} & Partial<API.RuleListItem>;

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
          roleIds: props.values.roleIds?.map(id => id.toString()),
          orgIds: props.values.orgIds?.map(id => id.toString()),
        }}
      >
          <ProFormText
            name="id"
            label="ID"
            hidden
            disabled
          />
          <ProFormText
            name="username"
            label={intl.formatMessage({
              id: 'pages.searchTable.updateForm.userName.nameLabel',
              defaultMessage: '用户名',
            })}
            width="md"
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
            width="md"
            label={intl.formatMessage({
              id: 'pages.searchTable.updateForm.userProps.userStatus',
              defaultMessage: '用户状态',
            })}
            options={userStatusOptions}
          />
          <ProFormSelect
            name="roleIds"
            mode="multiple"
            width="md"
            label={intl.formatMessage({
              id: 'pages.searchTable.updateForm.userProps.roleIds',
              defaultMessage: '角色',
            })}
            valueEnum={roleEnum}
          />
        <ProFormSelect
          name="orgIds"
          mode="multiple"
          width="md"
          label={intl.formatMessage({
            id: 'pages.searchTable.updateForm.userProps.orgIds',
            defaultMessage: '组织',
          })}
          valueEnum={orgEnum}
        />
      </ProForm>
    </Modal>
  );
};

export default UpdateForm;
