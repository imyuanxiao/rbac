import {
  ProForm,
  ProFormSelect,
  ProFormText, ProFormTextArea,
} from '@ant-design/pro-components';
import { FormattedMessage, useIntl } from '@umijs/max';
import {Modal} from 'antd';
import React from 'react';
import {orgEnum, permissionEnum, roleEnum, userStatusOptions} from "@/utils/comonValue";

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
  values: Partial<API.RoleListItem>;
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
          roleName: props.values.roleName,
          description: props.values.description,
          permissionIds: props.values.permissionIds?.map(id => id.toString()),
        }}
      >
          <ProFormText
            name="id"
            label="ID"
            hidden
            disabled
          />
          <ProFormText
            name="roleName"
            label={intl.formatMessage({
              id: 'pages.searchTable.updateForm.roleName.nameLabel',
              defaultMessage: '用户名',
            })}
            width="md"
            rules={[
              {
                required: true,
                message: (
                  <FormattedMessage
                    id="pages.searchTable.updateForm.roleName.nameRules"
                    defaultMessage="请输入角色名！"
                  />
                ),
              },
            ]}
          />
        <ProFormTextArea
          name="description"
          width="md"
          label={intl.formatMessage({
            id: 'pages.searchTable.updateForm.roleProps.description',
            defaultMessage: '描述',
          })}
        />
          <ProFormSelect
            name="permissionIds"
            mode="multiple"
            width="md"
            label={intl.formatMessage({
              id: 'pages.searchTable.updateForm.roleProps.permissionIds',
              defaultMessage: '权限',
            })}
            valueEnum={permissionEnum}
          />
      </ProForm>
    </Modal>
  );
};

export default UpdateForm;
