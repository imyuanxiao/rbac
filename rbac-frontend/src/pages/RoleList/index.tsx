import {
  addRole,
  getRolePage, removeRole,
  updateRole,
} from '@/services/ant-design-pro/api';
import { PlusOutlined } from '@ant-design/icons';
import type {ActionType, ProColumns, ProDescriptionsItemProps, ProFormInstance} from '@ant-design/pro-components';
import {
  FooterToolbar,
  ModalForm,
  PageContainer,
  ProDescriptions,
  ProFormText, ProFormTextArea, ProFormTreeSelect,
  ProTable,
} from '@ant-design/pro-components';
import { FormattedMessage, useIntl } from '@umijs/max';
import {Button, Drawer, message} from 'antd';
import React, { useRef, useState } from 'react';
import UpdateForm from './components/UpdateForm';
import {permissionEnum, permissionTreeEnum} from "@/utils/comonValue";
import { useAccess, Access } from 'umi';

/**
 * @en-US Add node
 * @zh-CN 添加节点
 * @param fields
 */
const handleAdd = async (fields: API.RoleListItem) => {
  const hide = message.loading('正在添加');
  try {
    await addRole(fields);
    hide();
    message.success('Added successfully');
    return true;
  } catch (error) {
    hide();
    message.error('Adding failed, please try again!');
    return false;
  }
};

/**
 * @en-US Update node
 * @zh-CN 更新节点
 *
 * @param fields
 */
const handleUpdate = async (fields: API.RoleListItem) => {
  const hide = message.loading('Updating');
  try {
    await updateRole(fields);
    hide();
    message.success('Update is successful');
    return true;
  } catch (error) {
    hide();
    message.error('Update failed, please try again!');
    return false;
  }
};

/**
 *  Delete node
 * @zh-CN 删除节点
 *
 * @param selectedRows
 */
const handleRemove = async (selectedRows: API.RoleListItem[]) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;
  try {
    await removeRole(selectedRows.map((row) => row.id));
    hide();
    message.success('Deleted successfully and will refresh soon');
    return true;
  } catch (error) {
    hide();
    message.error('Delete failed, please try again');
    return false;
  }
};

const RoleList: React.FC = () => {
  /**
   * @en-US Pop-up window of new window
   * @zh-CN 新建窗口的弹窗
   *  */
  const [createModalOpen, handleModalOpen] = useState<boolean>(false);
  /**
   * @en-US The pop-up window of the distribution update window
   * @zh-CN 分布更新窗口的弹窗
   * */
  const [updateModalOpen, handleUpdateModalOpen] = useState<boolean>(false);

  const [showDetail, setShowDetail] = useState<boolean>(false);

  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.RoleListItem>();
  const [selectedRowsState, setSelectedRows] = useState<API.RoleListItem[]>([]);
  const restFormRef = useRef<ProFormInstance>();

  const access = useAccess();

  const [currentPage, setCurrentPage] = useState(1); // 当前页码
  const [pageSize, setPageSize] = useState(10); // 每页显示的数据数量


  /**
   * @en-US International configuration
   * @zh-CN 国际化配置
   * */
  const intl = useIntl();

  const columns: ProColumns<API.RoleListItem>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
      hideInTable: true, // 隐藏该列
      hideInSearch: true, // 隐藏搜索条件
    },
    {
      title: (
        <FormattedMessage
          id="pages.searchTable.updateForm.index"
          defaultMessage="序号"
        />
      ),
      dataIndex: 'index',
      render: (dom, entity, index) => {
        const realIndex = (currentPage - 1) * pageSize + index + 1; // 计算真实的序号
        return realIndex;
      },
    },
    {
      title: (
        <FormattedMessage
          id="pages.searchTable.updateForm.roleName.nameLabel"
          defaultMessage="角色名"
        />
      ),
      dataIndex: 'roleName',
      render: (dom, entity) => {
        return (
          <a
            onClick={() => {
              setCurrentRow(entity);
              setShowDetail(true);
            }}
          >
            {dom}
          </a>
        );
      },
    },
    {
      title: <FormattedMessage id="pages.searchTable.description" defaultMessage="描述"/>,
      dataIndex: 'description',
    },
    {
      title: <FormattedMessage id="pages.searchTable.permissionIds" defaultMessage="权限" />,
      dataIndex: 'permissionIds',
      valueType: 'select',
      valueEnum: permissionEnum,
      fieldProps: {
        mode: 'multiple',
      },
    },
    {
      title: <FormattedMessage id="pages.searchTable.titleOption" defaultMessage="Operating" />,
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <Access
          key="editRoleOption"
          accessible={access.canEditRole}>
            <a
              onClick={() => {
                setCurrentRow(record);
                console.log('record', record)
                handleUpdateModalOpen(true);
              }}
            >
                <FormattedMessage id="pages.searchTable.edit" defaultMessage="编辑" />
            </a>
        </Access>

      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.RoleListItem, API.PageParams>
        headerTitle={intl.formatMessage({
          id: 'pages.searchTable.title',
          defaultMessage: '查询信息',
        })}
        actionRef={actionRef}
        rowKey="id"
        search={false}
        toolBarRender={() => [
          <Access
            key="tableAddUser"
            accessible={access.canAddRole}>
            <Button
              type="primary"
              onClick={() => {
                handleModalOpen(true);
              }}
            >
              <PlusOutlined /> <FormattedMessage id="pages.searchTable.new" defaultMessage="新增" />
            </Button>
          </Access>,
        ]}
        request={(params) => {
          // 更新当前页码和每页显示数量
          setCurrentPage(params.current || 1);
          setPageSize(params.pageSize || 10);
          // 调用实际的请求方法，传递 params 参数
          return getRolePage(params);
        }}
        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
        pagination={{
          pageSizeOptions: ['10', '20', '50'], // 自定义的每页显示数据数量选项
          defaultPageSize: 10, // 默认的每页显示数据数量
          showSizeChanger: true,
        }}
      />
      {selectedRowsState?.length > 0 && (
        <FooterToolbar
          extra={
            <div>
              <FormattedMessage id="pages.searchTable.chosen" defaultMessage="Chosen" />{' '}
              <a style={{ fontWeight: 600 }}>{selectedRowsState.length}</a>{' '}
              <FormattedMessage id="pages.searchTable.item" defaultMessage="项" />
            </div>
          }
        >
          <Access accessible={access.canDeleteRole}>
            <Button
              onClick={async () => {
                await handleRemove(selectedRowsState);
                setSelectedRows([]);
                actionRef.current?.reloadAndRest?.();
              }}
              danger={true}
            >
                <FormattedMessage
                  id="pages.searchTable.batchDeletion"
                  defaultMessage="Batch deletion"
                />
            </Button>
        </Access>
        </FooterToolbar>
      )}
      <ModalForm
        title={intl.formatMessage({
          id: 'pages.searchTable.createForm.new',
          defaultMessage: '新增',
        })}
        width="400px"
        formRef={restFormRef}
        open={createModalOpen}
        onOpenChange={handleModalOpen}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.RoleListItem);
          if (success) {
            handleModalOpen(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        submitter={{
          searchConfig: {
            resetText: '重置',
          },
          resetButtonProps: {
            onClick: () => {
              restFormRef.current?.resetFields();
            },
          },
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
        <ProFormTreeSelect
          name="permissionIds"
          placeholder="Please select"
          allowClear
          width={330}
          secondary
          label={intl.formatMessage({
            id: 'pages.searchTable.updateForm.roleProps.permissionIds',
            defaultMessage: '权限',
          })}
          request={async () => {
            return permissionTreeEnum;
          }}
          // tree-select args
          fieldProps={{
            showArrow: false,
            filterTreeNode: true,
            showSearch: true,
            popupMatchSelectWidth: false,
            labelInValue: true,
            autoClearSearchValue: true,
            multiple: true,
            treeNodeFilterProp: 'title',
            fieldNames: {
              label: 'title',
            },
            treeCheckable: true,
          }}
        />
      </ModalForm>
      <UpdateForm
        onSubmit={async (value) => {
          const success = await handleUpdate(value);
          if (success) {
            handleUpdateModalOpen(false);
            setCurrentRow(undefined);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        onCancel={() => {
          handleUpdateModalOpen(false);
          if (!showDetail) {
            setCurrentRow(undefined);
          }
        }}
        updateModalOpen={updateModalOpen}
        values={currentRow || {}}
      />

      <Drawer
        width={600}
        open={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.id && (
          <ProDescriptions<API.RoleListItem>
            column={2}
            title={
              <FormattedMessage id="pages.dataDetail.title" defaultMessage="详情" />
            }
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.id,
            }}
            columns={columns as ProDescriptionsItemProps<API.RoleListItem>[]}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};

export default RoleList;
