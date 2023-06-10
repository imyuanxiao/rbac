/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState: { currentUser?: API.CurrentUser } | undefined) {
  const { currentUser } = initialState ?? {};

  const permissionId = {
    canUserAdmin: 1000,
    canEditUser: 1003,
    canDeleteUser: 1002,
    canAddUser: 1001,
    canDataAdmin: 5000
  }

  return {
    // canAdmin: currentUser && currentUser.roleIds?.includes(1),
    canUserAdmin: (currentUser && currentUser.permissionIds?.includes(permissionId.canUserAdmin)) || false,
    canDataAdmin: (currentUser && currentUser.permissionIds?.includes(permissionId.canDataAdmin)) || false,
    canEditUser: (currentUser && currentUser.permissionIds?.includes(permissionId.canEditUser)) || false,
    canAddUser: (currentUser && currentUser.permissionIds?.includes(permissionId.canAddUser)) || false,
    canDeleteUser: (currentUser && (currentUser.permissionIds?.includes(permissionId.canDeleteUser))) || false,
  };
}

